package henrotaym.env.queues.listeners;

import henrotaym.env.enums.ProfileName;
import henrotaym.env.services.CharacterService;

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
public class SyncCharacterListener {

    private final CharacterService characterService;
    private static final Logger log = LoggerFactory.getLogger(SyncCharacterListener.class);

    public SyncCharacterListener(CharacterService characterService) {
        this.characterService = characterService;
    }

    @PostConstruct
    public void init() {
        log.info("✅ SyncCharacterListener initialisé !");
    }

    @KafkaListener(topics = "sync-character", groupId = "default")
    public void listen(String rawPageString, Acknowledgment ack) {
        try {

            String clean = rawPageString.replaceAll("^\"|\"$", "");
            int page = Integer.parseInt(clean);

            log.info("📘 Lancement de la synchronisation des personnages pour la page {}", page);
            characterService.syncCharactersFromApiPage(page);

            ack.acknowledge();

        } catch (NumberFormatException e) {
            log.error("❌ Erreur: valeur du message non numérique");
            ack.acknowledge(); // ou nack si tu veux rejouer
        } catch (Exception e) {
            log.info("Error unknown");
        }
    }
}
