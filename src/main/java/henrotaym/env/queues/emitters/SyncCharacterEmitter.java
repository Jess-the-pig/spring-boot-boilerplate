package henrotaym.env.queues.emitters;

import org.springframework.stereotype.Component;

import henrotaym.env.queues.events.SyncCharacterEvent;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SyncCharacterEmitter {
    private final Emitter emitter;

    public SyncCharacterEmitter(Emitter emitter) {
        this.emitter = emitter;
    }

    public void sendSyncCharactersEvent(SyncCharacterEvent event) {
        emitter.send(event);
    }
}
