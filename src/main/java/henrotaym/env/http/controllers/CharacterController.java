package henrotaym.env.http.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import henrotaym.env.entities.Character;
import henrotaym.env.services.CharacterService;

@RestController
@RequestMapping("/api/character")
public class CharacterController {
    private final CharacterService characterService;
    private final RestTemplate restTemplate;

    public CharacterController(CharacterService characterService, RestTemplate restTemplate) {
        this.characterService = characterService;
        this.restTemplate = restTemplate;
    }

    @GetMapping("path")
    public List<Character> findAllCharacters() {
        return characterService.findAll();
    }
}
