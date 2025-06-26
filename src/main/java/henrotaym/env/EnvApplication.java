package henrotaym.env;

import jakarta.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@SpringBootApplication
@Slf4j
@EnableScheduling
public class EnvApplication {

    @Autowired private Environment env;
    private static final Logger log = LoggerFactory.getLogger(EnvApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(EnvApplication.class, args);
    }

    @PostConstruct
    public void printActiveProfile() {
        log.info("🟡 Profil(s) actif(s): {}", Arrays.toString(env.getActiveProfiles()));
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /*
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
