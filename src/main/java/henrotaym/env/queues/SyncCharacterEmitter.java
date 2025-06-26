package henrotaym.env.queues;

import henrotaym.env.queues.events.SyncCharacterEvent;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class SyncCharacterEmitter {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public SyncCharacterEmitter(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendSyncCharactersEvent(SyncCharacterEvent event) {
        kafkaTemplate.send("sync-character", event);
    }
}
