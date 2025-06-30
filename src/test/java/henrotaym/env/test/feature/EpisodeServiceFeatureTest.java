import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import henrotaym.env.ApplicationTest;
import henrotaym.env.database.factories.CharacterFactory;
import henrotaym.env.entities.Character;
import henrotaym.env.repositories.CharacterRepository;
import henrotaym.env.services.EpisodeService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class EpisodeServiceFeatureTest extends ApplicationTest {
    @Autowired private EpisodeService episodeService;

    @Autowired private CharacterFactory characterFactory;

    @Autowired private CharacterRepository characterRepository;

    @Test
    void should_increment_episode_count_when_character_exists() {
        Long knownApiCharacterId = 9L;

        // Nettoyage
        characterRepository.deleteAll();

        // Création personnage unique
        Character character = characterFactory.createWithApiCHaracterId(knownApiCharacterId);
        Integer initialCount = character.getEpisodeCount();

        episodeService.syncEpisodesFromApiPage(2);

        Optional<Character> updatedCharacter =
                characterRepository.findByApiCharacterId(knownApiCharacterId);

        assertTrue(updatedCharacter.isPresent());
        assertTrue(updatedCharacter.get().getEpisodeCount() > initialCount);
    }

    @Test
    void should_do_nothing_when_character_not_found() {
        Long absentApiCharacterId = 99999L;
        characterRepository.deleteAll();

        episodeService.syncEpisodesFromApiPage(2);

        Optional<Character> result = characterRepository.findByApiCharacterId(absentApiCharacterId);
        assertFalse(result.isPresent());
    }
}
