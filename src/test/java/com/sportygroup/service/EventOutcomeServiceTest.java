// EventOutcomeServiceTest.java
package com.sportygroup.service;

import com.sportygroup.domain.entity.EventOutcomeEntity;
import com.sportygroup.domain.model.EventOutcome;
import com.sportygroup.events.EventOutcomeProducer;
import com.sportygroup.mapper.EventOutcomeMapper;
import com.sportygroup.repository.EventOutcomeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventOutcomeServiceTest {

    @Mock
    private EventOutcomeProducer eventOutcomeProducer;

    @Mock
    private EventOutcomeRepository eventOutcomeRepository;

    @Mock
    private EventOutcomeMapper eventOutcomeMapper;

    @InjectMocks
    private EventOutcomeService eventOutcomeService;

    private EventOutcome testEventOutcome;
    private EventOutcomeEntity testEventOutcomeEntity;

    @BeforeEach
    void setUp() {
        testEventOutcome = new EventOutcome("event1", "Test Event", "team1");
        testEventOutcomeEntity = new EventOutcomeEntity("event1", "Test Event", "team1");

        when(eventOutcomeMapper.modelToEntity(testEventOutcome)).thenReturn(testEventOutcomeEntity);
        when(eventOutcomeRepository.save(testEventOutcomeEntity)).thenReturn(testEventOutcomeEntity);
        when(eventOutcomeMapper.entityToModel(testEventOutcomeEntity)).thenReturn(testEventOutcome);
    }

    @Test
    void publishEventOutcome_ShouldSaveAndPublish() {
        // When
        EventOutcome result = eventOutcomeService.publishEventOutcome(testEventOutcome);

        // Then
        assertEquals(testEventOutcome, result);
        verify(eventOutcomeMapper, times(1)).modelToEntity(testEventOutcome);
        verify(eventOutcomeRepository, times(1)).save(testEventOutcomeEntity);
        verify(eventOutcomeMapper, times(1)).entityToModel(testEventOutcomeEntity);
        verify(eventOutcomeProducer, times(1)).sendEventOutcome(testEventOutcome);
    }
}