package henrotaym.env;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import henrotaym.env.queues.emitters.SyncCharacterEmitter;
import henrotaym.env.queues.events.SyncCharacterEvent;
import lombok.extern.slf4j.Slf4j;

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
    CommandLineRunner startSyncEmitter(SyncCharacterEmitter syncCharacterEmitter) {
        return args -> {
            // Par exemple, on démarre la synchro pour la page 1
            SyncCharacterEvent event = new SyncCharacterEvent(1);
            syncCharacterEmitter.sendSyncCharactersEvent(event);
            // Tu peux aussi boucler sur plusieurs pages si tu veux
        };
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
