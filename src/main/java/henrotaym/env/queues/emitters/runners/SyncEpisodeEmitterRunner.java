package henrotaym.env.queues.emitters.runners;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import henrotaym.env.enums.EventName;
import henrotaym.env.queues.emitters.SyncEpisodeEmitter;
import henrotaym.env.queues.events.SyncEpisodeEvent;

@Component
public class SyncEpisodeEmitterRunner
        implements CommandLineRunner, ApplicationListener<ContextClosedEvent> {

    private static final Logger log = LoggerFactory.getLogger(SyncEpisodeEmitterRunner.class);
    private final SyncEpisodeEmitter syncEpisodeEmitter;
    private ScheduledExecutorService scheduler;
    private String eventName = EventName.SYNC_EPISODE;

    public SyncEpisodeEmitterRunner(SyncEpisodeEmitter syncEpisodeEmitter) {
        this.syncEpisodeEmitter = syncEpisodeEmitter;
    }

    @Override
    public void run(String... args) {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(
                () -> {
                    try {
                        SyncEpisodeEvent event = new SyncEpisodeEvent(eventName, 1);
                        log.info("Envoi périodique de l'événement episode : {}", event);
                        if (syncEpisodeEmitter != null) {
                            syncEpisodeEmitter.sendSyncEpisodesEvent(event);
                        } else {
                            log.error("syncEpisodeEmitter est null !");
                        }
                    } catch (Exception e) {
                        log.error("Erreur lors de l’envoi de l’événement : {}", e.getMessage(), e);
                    }
                },
                0,
                150,
                TimeUnit.SECONDS);
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        if (scheduler != null) {
            scheduler.shutdown();
            try {
                if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                    scheduler.shutdownNow();
                }
            } catch (InterruptedException e) {
                scheduler.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }
}
