package henrotaym.env;

import henrotaym.env.queues.events.SyncCharacterEvent;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    private final KafkaProperties kafkaProperties;

    public KafkaConsumerConfig(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>(kafkaProperties.buildConsumerProperties());
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(
                ConsumerConfig.GROUP_ID_CONFIG,
                "mon-groupe"); // Utilise le même groupId que dans le listener
        // Ajoute le package à trust
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "henrotaym.env.queues.events");
        props.put(
                ConsumerConfig.INTERCEPTOR_CLASSES_CONFIG,
                "henrotaym.env.interceptors.LoggingConsumerInterceptor");
        return props;
    }

    @Bean
    public ConsumerFactory<String, SyncCharacterEvent> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                consumerConfigs(),
                new StringDeserializer(),
                new JsonDeserializer<>(SyncCharacterEvent.class));
    }
}
