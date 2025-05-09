// EventOutcomeConsumer.java
package com.sportygroup.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sportygroup.domain.model.EventOutcome;
import com.sportygroup.service.BetSettlementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventOutcomeConsumer {
    private final ObjectMapper objectMapper;
    private final BetSettlementService betSettlementService;

    @KafkaListener(topics = "event-outcomes", groupId = "betting-service")
    public void consumeEventOutcome(String message) {
        try {
            log.info("Received event outcome message: {}", message);
            EventOutcome eventOutcome = objectMapper.readValue(message, EventOutcome.class);
            log.info("Deserialized event outcome: {}", eventOutcome);

            try {
                betSettlementService.processEventOutcome(eventOutcome);
            } catch (Exception e) {
                // Catch and log errors to prevent message reprocessing
                log.error("Error processing event outcome: {}", e.getMessage(), e);
            }
        } catch (JsonProcessingException e) {
            log.error("Error deserializing event outcome: {}", e.getMessage(), e);
        } catch (Exception e) {
            log.error("Unexpected error processing event outcome: {}", e.getMessage(), e);
        }
    }
}