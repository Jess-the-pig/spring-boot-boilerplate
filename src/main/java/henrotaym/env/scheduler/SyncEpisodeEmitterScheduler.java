package henrotaym.env.scheduler;

import henrotaym.env.queues.emitters.SyncEpisodeEmitter;
import henrotaym.env.queues.events.SyncEpisodeEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SyncEpisodeEmitterScheduler {

    private static final Logger log = LoggerFactory.getLogger(SyncEpisodeEmitterScheduler.class);
    private final SyncEpisodeEmitter syncEpisodeEmitter;

    public SyncEpisodeEmitterScheduler(SyncEpisodeEmitter syncEpisodeEmitter) {
        this.syncEpisodeEmitter = syncEpisodeEmitter;
    }

    @Scheduled(fixedRate = 100000)
    public void sendSyncEvent() {
        SyncEpisodeEvent event = new SyncEpisodeEvent("sync-episode", 1);
        log.info("Envoi périodique de l'événement : {}", event);
        syncEpisodeEmitter.sendSyncEpisodesEvent(event);
    }
}
