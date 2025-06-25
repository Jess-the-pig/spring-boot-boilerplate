package henrotaym.env.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import henrotaym.env.entities.Character;
import henrotaym.env.repositories.CharacterRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CharacterService {
    private static final Logger log = LoggerFactory.getLogger(Character.class);

    private final CharacterRepository characterRepository;
    private final RestTemplate restTemplate;

    public CharacterService(CharacterRepository characterRepository, RestTemplate restTemplate) {
        this.characterRepository = characterRepository;
        this.restTemplate = restTemplate;
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
        for (Character character : charactersFromApi) {
            Optional<Character> existing = characterRepository.findById(character.getId());
            if (existing.isPresent()) {
                Character toUpdate = existing.get();
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
}
