package henrotaym.env;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@Slf4j
public class EnvApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnvApplication.class, args);
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /* Test de base de recherche d'API RickEtMorty
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
        */
}
