package henrotaym.env.queues.emitters;

import com.fasterxml.jackson.databind.ObjectMapper;

import henrotaym.env.queues.events.SyncCharacterEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class SyncCharacterEmitter {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final Logger log = LoggerFactory.getLogger(SyncCharacterEmitter.class);

    public SyncCharacterEmitter(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendSyncCharactersEvent(SyncCharacterEvent event) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(event.getPage());
            kafkaTemplate.send(event.eventName(), json);
            System.out.println("Je suis envoyer a kafka");
            System.out.println("Message envoyé à Kafka : " + json);
        } catch (Exception e) {
            log.error("Erreur lors de la sérialisation de l'événement : ", e);
        }
    }
}
