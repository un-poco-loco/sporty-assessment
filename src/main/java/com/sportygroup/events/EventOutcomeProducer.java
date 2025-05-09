// EventOutcomeProducer.java
package com.sportygroup.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sportygroup.domain.model.EventOutcome;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventOutcomeProducer {
    private static final String TOPIC = "event-outcomes";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void sendEventOutcome(EventOutcome eventOutcome) {
        try {
            String eventOutcomeJson = objectMapper.writeValueAsString(eventOutcome);
            kafkaTemplate.send(TOPIC, eventOutcome.getEventId(), eventOutcomeJson);
            log.info("Event outcome sent to Kafka: {}", eventOutcome);
        } catch (JsonProcessingException e) {
            log.error("Error serializing event outcome: {}", e.getMessage());
            throw new RuntimeException("Failed to serialize event outcome", e);
        }
    }
}



