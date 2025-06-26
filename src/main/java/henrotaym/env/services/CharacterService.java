package henrotaym.env.services;

import henrotaym.env.entities.Character; // ← à adapter selon ton package
import henrotaym.env.repositories.CharacterRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j // On garde Lombok pour le logger
public class CharacterService {

    private final CharacterRepository characterRepository;
    private final RestTemplate restTemplate;
    private final JsonPlaceholderService jsonPlaceholderService;
    private final EntityManager entityManager;

    public CharacterService(
            CharacterRepository characterRepository,
            RestTemplate restTemplate,
            JsonPlaceholderService jsonPlaceholderService,
            EntityManager entityManager // ← injection ajoutée
            ) {
        this.characterRepository = characterRepository;
        this.restTemplate = restTemplate;
        this.jsonPlaceholderService = jsonPlaceholderService;
        this.entityManager = entityManager;
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

    @Transactional
    public void updateOrCreateAllFromApi(List<Character> charactersFromApi) {
        for (Character apiChar : charactersFromApi) {
            // On suppose que l'entité Character a un champ "apiCharacterId"
            Long apiCharacterId = apiChar.getapiCharacterID();

            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Character> cq = cb.createQuery(Character.class);
            Root<Character> root = cq.from(Character.class);
            cq.where(cb.equal(root.get("apiCharacterId"), apiCharacterId));

            Optional<Character> existingOpt =
                    entityManager.createQuery(cq).getResultStream().findFirst();

            if (existingOpt.isPresent()) {
                Character toUpdate = existingOpt.get();
                // Copie les champs nécessaires
                toUpdate.setName(apiChar.getName());
                toUpdate.setStatus(apiChar.getStatus());
                toUpdate.setImage(apiChar.getImage());
                characterRepository.save(toUpdate);
            } else {
                characterRepository.save(apiChar);
            }
        }
    }

    @Transactional
    public void syncCharactersFromApiPage(int page) {
        List<Character> characters = jsonPlaceholderService.getCharacters(page);
        updateOrCreateAllFromApi(characters);
    }
}
