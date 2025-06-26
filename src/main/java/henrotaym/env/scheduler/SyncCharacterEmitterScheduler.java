package henrotaym.env.scheduler;

import henrotaym.env.queues.SyncCharacterEmitter;
import henrotaym.env.queues.events.SyncCharacterEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SyncCharacterEmitterScheduler {

    private static final Logger log = LoggerFactory.getLogger(SyncCharacterEmitterScheduler.class);
    private final SyncCharacterEmitter syncCharacterEmitter;

    public SyncCharacterEmitterScheduler(SyncCharacterEmitter syncCharacterEmitter) {
        this.syncCharacterEmitter = syncCharacterEmitter;
    }

    @Scheduled(fixedRate = 10000) // toutes les 10 secondes
    public void sendSyncEvent() {
        SyncCharacterEvent event = new SyncCharacterEvent("sync-character", "1");
        log.info("Envoi périodique de l'événement : {}", event);
        syncCharacterEmitter.sendSyncCharactersEvent(event);
    }
}
