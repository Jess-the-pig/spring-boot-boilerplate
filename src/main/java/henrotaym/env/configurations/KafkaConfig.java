package henrotaym.env.configurations;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    /** Topic pour la synchronisation des personnages. */
    @Bean
    public NewTopic syncCharacterTopic() {
        return TopicBuilder.name("sync-character")
                .partitions(3) // ⚠️ Adapter selon le nombre de consommateurs/charge
                .replicas(1) // ⚠️ Doit correspondre au nombre de brokers disponibles
                .build();
    }

    /** Topic pour la synchronisation des épisodes. */
    @Bean
    public NewTopic syncEpisodeTopic() {
        return TopicBuilder.name("sync-episode").partitions(3).replicas(1).build();
    }
}
