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
import henrotaym.env.queues.emitters.SyncCharacterEmitter;
import henrotaym.env.queues.events.SyncCharacterEvent;

@Component
public class SyncCharacterEmitterRunner
        implements CommandLineRunner, ApplicationListener<ContextClosedEvent> {

    private static final Logger log = LoggerFactory.getLogger(SyncCharacterEmitterRunner.class);
    private final SyncCharacterEmitter syncCharacterEmitter;
    private ScheduledExecutorService scheduler;
    private String eventName = EventName.SYNC_CHARACTER;

    public SyncCharacterEmitterRunner(SyncCharacterEmitter syncCharacterEmitter) {
        this.syncCharacterEmitter = syncCharacterEmitter;
    }

    @Override
    public void run(String... args) {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(
                () -> {
                    try {
                        SyncCharacterEvent event = new SyncCharacterEvent(eventName, 1);
                        log.info("Envoi périodique de l'événement : {}", event);
                        if (syncCharacterEmitter != null) {
                            syncCharacterEmitter.sendSyncCharactersEvent(event);
                        } else {
                            log.error("syncCharacterEmitter est null !");
                        }
                    } catch (Exception e) {
                        log.error("Erreur lors de l’envoi de l’événement : {}", e.getMessage(), e);
                        // NE PAS lancer RuntimeException("Compile Error") ici
                    }
                },
                0, // délai initial (0 = immédiat)
                100, // délai entre chaque exécution
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
