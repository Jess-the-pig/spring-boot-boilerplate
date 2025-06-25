package henrotaym.env.services;

import henrotaym.env.entities.Character;
import henrotaym.env.repositories.CharacterRepository;

import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

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
}
