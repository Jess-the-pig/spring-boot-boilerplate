package henrotaym.env.services;

import henrotaym.env.entities.Episode;
import henrotaym.env.repositories.CharacterRepository;
import henrotaym.env.repositories.EpisodeRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class EpisodeService {
    private static final Logger log = LoggerFactory.getLogger(Episode.class);

    private final EpisodeRepository episodeRepository;
    private final RestTemplate restTemplate;
    private final EntityManager entityManager;
    private final JsonPlaceholderService jsonPlaceholderService;
    private final CharacterRepository characterRepository;

    public EpisodeService(
            EpisodeRepository episodeRepository,
            RestTemplate restTemplate,
            EntityManager entityManager,
            JsonPlaceholderService jsonPlaceholderService,
            CharacterRepository characterRepository) {
        this.episodeRepository = episodeRepository;
        this.restTemplate = restTemplate;
        this.entityManager = entityManager;
        this.jsonPlaceholderService = jsonPlaceholderService;
        this.characterRepository = characterRepository;
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
            Long apiEpisodeId = episode.getApiEpisodeId();

            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Episode> cq = cb.createQuery(Episode.class);
            Root<Episode> root = cq.from(Episode.class);
            cq.where(cb.equal(root.get("apiEpisodeId"), apiEpisodeId));

            Optional<Episode> existingOpt =
                    entityManager.createQuery(cq).getResultStream().findFirst();

            if (existingOpt.isPresent()) {
                Episode toUpdate = existingOpt.get();
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

    @Transactional
    public void syncEpisodesFromApiPage(int page) {
        List<Episode> episodes = jsonPlaceholderService.getEpisodes(page);
        updateOrCreateAllFromApi(episodes);

        // Pour chaque épisode, on met à jour les personnages concernés
        for (Episode episode : episodes) {
            // Supposons qu'on ait une méthode pour extraire les characterIds à partir d'un épisode
            List<Long> characterIds = extractCharacterIdsFromEpisode(episode);

            for (Long charId : characterIds) {
                characterRepository
                        .findByApiCharacterId(charId)
                        .ifPresent(
                                character -> {
                                    List<String> episodeUrls = character.getEpisodeUrls();
                                    if (episodeUrls == null) {
                                        episodeUrls = new ArrayList<>();
                                    }

                                    // Construire l'URL de l'épisode (format à adapter selon ton
                                    // API)
                                    String episodeUrl = "/api/episode/" + episode.getApiEpisodeId();

                                    if (!episodeUrls.contains(episodeUrl)) {
                                        episodeUrls.add(episodeUrl);
                                        character.setEpisodeUrls(
                                                episodeUrls); // met à jour episodeCount
                                        // automatiquement
                                        characterRepository.save(character);
                                    }
                                });
            }
        }
    }

    private List<Long> extractCharacterIdsFromEpisode(Episode episode) {

        if (episode.getCharacterUrls() == null) {
            return List.of();
        }

        return episode.getCharacterUrls().stream()
                .map(
                        url -> {
                            try {
                                String[] parts = url.split("/");
                                return Long.parseLong(parts[parts.length - 1]);
                            } catch (Exception e) {
                                log.warn("Impossible d'extraire l'id personnage de l'URL: " + url);
                                return null;
                            }
                        })
                .filter(id -> id != null)
                .toList();
    }
}
