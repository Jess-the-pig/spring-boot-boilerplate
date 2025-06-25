package henrotaym.env.services;

import com.querydsl.core.types.dsl.BooleanExpression;

import henrotaym.env.repositories.CharacterRepository;

import jakarta.transaction.Transactional;

import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CharacterService {
    private static final Logger log = LoggerFactory.getLogger(Character.class);

    private final CharacterRepository characterRepository;
    private final RestTemplate restTemplate;
    private final JsonPlaceholderService jsonPlaceholderService;
    private final QCharacter qCharacter = QCharacter.character;

    public CharacterService(
            CharacterRepository characterRepository,
            RestTemplate restTemplate,
            JsonPlaceholderService jsonPlaceholderService) {
        this.characterRepository = characterRepository;
        this.restTemplate = restTemplate;
        this.jsonPlaceholderService = jsonPlaceholderService;
    }

    public List<Character> findAll() {
        return characterRepository.findAll();
    }

    public void saveAll(List<Character> characters) {
        characterRepository.saveAll(characters);
    }

    public void deleteAll() {
        characterRepository.deleteAll();
    }

    @Transactional
    public void refreshAllFromApi(List<Character> charactersFromApi) {
        characterRepository.deleteAll();
        characterRepository.saveAll(charactersFromApi);
    }

    public void updateOrCreateAllFromApi(List<Character> charactersFromApi) {
        QCharacter qCharacter = QCharacter.character;

        for (Character apiChar : charactersFromApi) {
            BooleanExpression predicate = qCharacter.apiCharacterId.eq(apiChar.getApiCharacterId());

            Optional<Character> existingOpt = characterRepository.findOne(predicate);

            if (existingOpt.isPresent()) {
                Character toUpdate = existingOpt.get();
                // Copie les champs nécessaires
                toUpdate.setName(character.getName());
                toUpdate.setStatus(character.getStatus());
                toUpdate.setImage(character.getImage());
                characterRepository.save(toUpdate);
            } else {
                characterRepository.save(character);
            }
        }
    }

    @Transactional
    public void syncCHaractersFromApiPage(Integer page) {
        List<Character> characters = jsonPlaceholderService.getCharacters(page);
        updateOrCreateAllFromApi(characters);
    }
}
