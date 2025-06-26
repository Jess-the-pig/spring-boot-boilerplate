package henrotaym.env.queues.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;

import henrotaym.env.enums.ProfileName;
import henrotaym.env.queues.events.SyncCharacterEvent;
import henrotaym.env.services.CharacterService;

import jakarta.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Slf4j
@Component
@Profile(ProfileName.QUEUE)
public class SyncCharacterListener {

    private final CharacterService characterService;
    private final ObjectMapper objectMapper;
    private static final Logger log = LoggerFactory.getLogger(SyncCharacterListener.class);

    public SyncCharacterListener(CharacterService characterService, ObjectMapper objectMapper) {
        this.characterService = characterService;
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void init() {
        log.info("✅ SyncCharacterListener initialisé !");
    }

    @KafkaListener(topics = "sync-character", groupId = "default")
    public void listen(ConsumerRecord<String, String> record, Acknowledgment ack) {
        try {
            log.info(
                    "Message reçu - Topic: {}, Partition: {}, Offset: {}, Key: {}, Value: {}",
                    record.topic(),
                    record.partition(),
                    record.offset(),
                    record.key(),
                    record.value());

            // Désérialisation du message
            SyncCharacterEvent event =
                    objectMapper.readValue(record.value(), SyncCharacterEvent.class);

            System.out.println("Je suis dans le listener");
            characterService.syncCharactersFromApiPage(event.getPage());

            // Ack manuel (sécurisé)
            ack.acknowledge();

        } catch (Exception e) {
            log.error(
                    "Erreur lors du traitement du message: Topic={}, Partition={}, Offset={},"
                            + " Value={} - Erreur: {}",
                    record.topic(),
                    record.partition(),
                    record.offset(),
                    record.value(),
                    e.getMessage());
            // Nack pour rejouer le message plus tard (optionnel)
            ack.nack(Duration.ofSeconds(5));
        }
    }
}
