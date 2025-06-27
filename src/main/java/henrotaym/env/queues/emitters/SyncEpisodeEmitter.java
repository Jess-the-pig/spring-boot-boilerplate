package henrotaym.env.queues.emitters;

import com.fasterxml.jackson.databind.ObjectMapper;

import henrotaym.env.queues.events.SyncEpisodeEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class SyncEpisodeEmitter {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final Logger log = LoggerFactory.getLogger(SyncEpisodeEmitter.class);

    public SyncEpisodeEmitter(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendSyncEpisodesEvent(SyncEpisodeEvent event) {
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
