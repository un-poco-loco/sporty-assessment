// RocketMQProducer.java
package com.sportygroup.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sportygroup.domain.model.BetSettlement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RocketMQProducerMock {
    private static final String TOPIC = "bet-settlements";

    private final ObjectMapper objectMapper;

    public void sendBetSettlement(BetSettlement betSettlement) {
        try {
            String betSettlementJson = objectMapper.writeValueAsString(betSettlement);
            // Just log the payload as per the assignment's suggestion
            log.info("RocketMQ (mocked) message to topic {}: {}", TOPIC, betSettlementJson);
        } catch (JsonProcessingException e) {
            log.error("Error serializing bet settlement: {}", e.getMessage());
        }
    }
}