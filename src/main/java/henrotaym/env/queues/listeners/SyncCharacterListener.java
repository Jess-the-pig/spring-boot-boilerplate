package henrotaym.env.queues.listeners;

import henrotaym.env.enums.ProfileName;
import henrotaym.env.queues.events.SyncCharacterEvent;
import henrotaym.env.services.CharacterService;

import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile(ProfileName.QUEUE)
public class SyncCharacterListener {

    private final CharacterService characterService;
    private static final Logger log = LoggerFactory.getLogger(SyncCharacterListener.class);

    public SyncCharacterListener(CharacterService characterService) {
        this.characterService = characterService;
    }

    @KafkaListener(topics = "sync-character")
    public void listen(SyncCharacterEvent event) {
        characterService.syncCharactersFromApiPage(event.getPage());
    }
}
