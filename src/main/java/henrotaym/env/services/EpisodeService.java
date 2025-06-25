package henrotaym.env.services;

import henrotaym.env.entities.Episode;
import henrotaym.env.repositories.EpisodeRepository;

import jakarta.transaction.Transactional;

import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class EpisodeService {
    private static final Logger log = LoggerFactory.getLogger(Episode.class);

    private final EpisodeRepository episodeRepository;
    private final RestTemplate restTemplate;

    public EpisodeService(EpisodeRepository episodeRepository, RestTemplate restTemplate) {
        this.episodeRepository = episodeRepository;
        this.restTemplate = restTemplate;
    }

    public List<Episode> findAll() {
        return episodeRepository.findAll();
    }

    public void saveAll(List<Episode> episodes) {
        episodeRepository.saveAll(episodes);
    }

    public void deleteAll() {
        episodeRepository.deleteAll();
    }

    @Transactional
    public void refreshAllFromApi(List<Episode> episodesFromApi) {
        episodeRepository.deleteAll();
        episodeRepository.saveAll(episodesFromApi);
    }

    public void updateOrCreateAllFromApi(List<Episode> episodesFromApi) {
        for (Episode episode : episodesFromApi) {
            Optional<Episode> existing = episodeRepository.findById(episode.getId());
            if (existing.isPresent()) {
                Episode toUpdate = existing.get();
                // Copie les champs nécessaires
                toUpdate.setName(episode.getName());
                toUpdate.setApiEpisodeId(episode.getApiEpisodeId());
                toUpdate.setEpisodeCode(episode.getEpisodeCode());
                episodeRepository.save(toUpdate);
            } else {
                episodeRepository.save(episode);
            }
        }
    }
}
