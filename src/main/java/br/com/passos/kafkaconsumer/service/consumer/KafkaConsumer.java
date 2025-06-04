package br.com.passos.kafkaconsumer.service.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Headers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "${kafka.topic}")
    public void consumeTopic(ConsumerRecord<String, String> consumerRecord) {
        logRecordData(consumerRecord);
        String json = consumerRecord.value();
        log.info(json);
    }

    private static void logRecordData(ConsumerRecord<String, String> consumerRecord) {
        try {
            long timestamp = consumerRecord.timestamp();
            long offset = consumerRecord.offset();
            String topic = consumerRecord.topic();
            long partition = consumerRecord.partition();
            String key = consumerRecord.key();
            String value = consumerRecord.value();
            Headers headers = consumerRecord.headers();

            log.info("Timestamp: {}, Offset: {}, Topic: {}, Partition: {}, Key: {}, Value: {}, Headers: {}",
                    timestamp, offset, topic, partition, key, value, headers);
        } catch (Exception e) {
            log.error("Error on getting log record data", e);
        }
    }
}
