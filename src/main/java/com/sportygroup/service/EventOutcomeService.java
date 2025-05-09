// EventOutcomeService.java
package com.sportygroup.service;

import com.sportygroup.domain.model.EventOutcome;
import com.sportygroup.domain.entity.EventOutcomeEntity;
import com.sportygroup.events.EventOutcomeProducer;
import com.sportygroup.mapper.EventOutcomeMapper;
import com.sportygroup.repository.EventOutcomeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventOutcomeService {
    private final EventOutcomeProducer eventOutcomeProducer;
    private final EventOutcomeRepository eventOutcomeRepository;
    private final EventOutcomeMapper eventOutcomeMapper;

    @Transactional
    public EventOutcome publishEventOutcome(EventOutcome eventOutcome) {
        log.info("Publishing event outcome: {}", eventOutcome);

        // Convert model to entity and save to database
        EventOutcomeEntity entity = eventOutcomeMapper.modelToEntity(eventOutcome);
        EventOutcomeEntity savedEntity = eventOutcomeRepository.save(entity);

        // Convert saved entity back to model
        EventOutcome savedOutcome = eventOutcomeMapper.entityToModel(savedEntity);

        // Publish to Kafka
        eventOutcomeProducer.sendEventOutcome(savedOutcome);

        return savedOutcome;
    }
}



