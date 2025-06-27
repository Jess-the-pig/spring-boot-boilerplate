package henrotaym.env.queues.listeners;

import henrotaym.env.enums.ProfileName;
import henrotaym.env.services.EpisodeService;

import jakarta.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile(ProfileName.QUEUE)
public class SyncEpisodeListener {

    private final EpisodeService episodeService;
    private static final Logger log = LoggerFactory.getLogger(SyncEpisodeListener.class);

    public SyncEpisodeListener(EpisodeService episodeService) {
        this.episodeService = episodeService;
    }

    @PostConstruct
    public void init() {
        log.info("✅ SyncCharacterListener initialisé !");
    }

    @KafkaListener(topics = "sync-episode", groupId = "default")
    public void listen(String rawPageString, Acknowledgment ack) {
        try {

            String clean = rawPageString.replaceAll("^\"|\"$", "");
            int page = Integer.parseInt(clean);

            log.info("📘 Lancement de la synchronisation des personnages pour la page {}", page);
            episodeService.syncEpisodesFromApiPage(page);

            ack.acknowledge();

        } catch (NumberFormatException e) {
            log.error("❌ Erreur: valeur du message non numérique");
            ack.acknowledge(); // ou nack si tu veux rejouer
        } catch (Exception e) {
            log.info("Error unknown");
        }
    }
}
