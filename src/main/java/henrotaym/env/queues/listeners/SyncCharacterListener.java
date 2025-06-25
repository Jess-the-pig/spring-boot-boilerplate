package henrotaym.env.queues.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import henrotaym.env.annotations.KafkaRetryableListener;
import henrotaym.env.enums.ProfileName;
import henrotaym.env.queues.events.SyncCharacterEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Profile(ProfileName.QUEUE)
public class SyncCharacterListener implements Listener<SyncCharacterEvent> {

    private static final Logger log = LoggerFactory.getLogger(SyncCharacterEvent.class);

    @Override
    @KafkaRetryableListener(SyncCharacterEvent.EVENT_NAME)
    public void listen(SyncCharacterEvent syncCharacterEvent) {
        log.info("Synchronisation character recu du topic {}: {}", syncCharacterEvent.eventName());
    }
}
