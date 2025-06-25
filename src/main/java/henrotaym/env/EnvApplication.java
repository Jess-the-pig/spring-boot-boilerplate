package henrotaym.env;

import henrotaym.env.entities.Character;
import henrotaym.env.entities.Episode;
import henrotaym.env.services.CharacterService;
import henrotaym.env.services.EpisodeService;
import henrotaym.env.services.JsonPlaceholderService;

import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootApplication
@Slf4j
public class EnvApplication {

    private static final Logger log = LoggerFactory.getLogger(EnvApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(EnvApplication.class, args);
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    CommandLineRunner commandLineRunner(
            JsonPlaceholderService placeHolderService,
            CharacterService characterService,
            EpisodeService episodeService) {
        return args -> {
            List<Character> characters = placeHolderService.getCharacters();
            List<Episode> episodes = placeHolderService.getEpisodes();
            characterService.refreshAllFromApi(characters);
            log.info("Refreshed {} characters in the database", characters.size());
            episodeService.refreshAllFromApi(episodes);
            log.info("Refreshed {} episodes in the database", episodes.size());
        };
    }
}
