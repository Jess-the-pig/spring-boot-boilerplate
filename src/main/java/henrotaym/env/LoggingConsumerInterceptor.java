package henrotaym.env;

import org.apache.kafka.clients.consumer.ConsumerInterceptor;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class LoggingConsumerInterceptor implements ConsumerInterceptor<String, String> {

    private static final Logger log = LoggerFactory.getLogger(LoggingConsumerInterceptor.class);

    @Override
    public ConsumerRecords<String, String> onConsume(ConsumerRecords<String, String> records) {
        // Loggue le nombre de messages reçus
        log.info("ConsumerInterceptor: {} messages reçus", records.count());
        // Si tu veux logguer le contenu (attention, cela peut être volumineux)
        records.forEach(
                record ->
                        log.info(
                                "Message reçu (avant désérialisation) - Topic: {}, Partition: {},"
                                        + " Offset: {}, Key: {}, Value: {}",
                                record.topic(),
                                record.partition(),
                                record.offset(),
                                record.key(),
                                record.value()));
        return records;
    }

    @Override
    public void onCommit(Map<TopicPartition, OffsetAndMetadata> offsets) {
        // Loggue les offsets commités
        log.info("ConsumerInterceptor: Offsets commités: {}", offsets);
    }

    @Override
    public void close() {
        // Nettoyage éventuel
    }

    @Override
    public void configure(Map<String, ?> configs) {
        // Configuration de l'interceptor
    }
}
