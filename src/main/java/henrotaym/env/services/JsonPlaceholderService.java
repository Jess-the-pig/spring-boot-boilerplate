package henrotaym.env.services;

import com.fasterxml.jackson.databind.ObjectMapper;

import henrotaym.env.entities.Character;
import henrotaym.env.entities.Episode;
import henrotaym.env.response.CharacterListResponse;
import henrotaym.env.response.EpisodeListResponse;

import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
public class JsonPlaceholderService {
    private final String RICK_API_URL = "https://rickandmortyapi.com/api";
    private final RestTemplate restTemplate;
    private final Logger log = LoggerFactory.getLogger(getClass());

    public JsonPlaceholderService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Character> getCharacters(int page) {
        String url = RICK_API_URL;
        ResponseEntity<String> response =
                restTemplate.exchange(
                        url + "/character?page=" + page, HttpMethod.GET, null, String.class);
        String body = response.getBody();
        log.info("Réponse brute reçue : {}", body);

        try {
            ObjectMapper mapper = new ObjectMapper();
            // Crée une classe qui encapsule la liste de drivers
            CharacterListResponse wrapper = mapper.readValue(body, CharacterListResponse.class);
            return wrapper.getCharacters();
        } catch (Exception e) {
            log.error("Erreur lors du parsing de la réponse : {}", e.getMessage());
            throw new RuntimeException("Erreur lors du parsing de la réponse", e);
        }
    }

    public List<Episode> getEpisodes() {
        String url = RICK_API_URL;
        ResponseEntity<String> response =
                restTemplate.exchange(url + "/episode", HttpMethod.GET, null, String.class);
        String body = response.getBody();
        log.info("Réponse brute reçue : {}", body);

        try {
            ObjectMapper mapper = new ObjectMapper();
            // Crée une classe qui encapsule la liste de drivers
            EpisodeListResponse wrapper = mapper.readValue(body, EpisodeListResponse.class);
            return wrapper.getEpisodes();
        } catch (Exception e) {
            log.error("Erreur lors du parsing de la réponse : {}", e.getMessage());
            throw new RuntimeException("Erreur lors du parsing de la réponse", e);
        }
    }
}
