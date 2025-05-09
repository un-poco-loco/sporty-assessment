// EventOutcomeMapperTest.java
package com.sportygroup.mapper;

import com.sportygroup.domain.dto.EventOutcomeDto;
import com.sportygroup.domain.entity.EventOutcomeEntity;
import com.sportygroup.domain.model.EventOutcome;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EventOutcomeMapperTest {

    private final EventOutcomeMapper mapper = Mappers.getMapper(EventOutcomeMapper.class);

    @Test
    void entityToModel_shouldMapCorrectly() {
        // Given
        EventOutcomeEntity entity = new EventOutcomeEntity("event1", "Test Event", "team1");

        // When
        EventOutcome model = mapper.entityToModel(entity);

        // Then
        assertNotNull(model);
        assertEquals(entity.getEventId(), model.getEventId());
        assertEquals(entity.getEventName(), model.getEventName());
        assertEquals(entity.getEventWinnerId(), model.getEventWinnerId());
    }

    @Test
    void modelToEntity_shouldMapCorrectly() {
        // Given
        EventOutcome model = new EventOutcome("event1", "Test Event", "team1");

        // When
        EventOutcomeEntity entity = mapper.modelToEntity(model);

        // Then
        assertNotNull(entity);
        assertEquals(model.getEventId(), entity.getEventId());
        assertEquals(model.getEventName(), entity.getEventName());
        assertEquals(model.getEventWinnerId(), entity.getEventWinnerId());
    }

    @Test
    void modelToDto_shouldMapCorrectly() {
        // Given
        EventOutcome model = new EventOutcome("event1", "Test Event", "team1");

        // When
        EventOutcomeDto dto = mapper.modelToDto(model);

        // Then
        assertNotNull(dto);
        assertEquals(model.getEventId(), dto.eventId());
        assertEquals(model.getEventName(), dto.eventName());
        assertEquals(model.getEventWinnerId(), dto.eventWinnerId());
    }

    @Test
    void dtoToModel_shouldMapCorrectly() {
        // Given
        EventOutcomeDto dto = new EventOutcomeDto("event1", "Test Event", "team1");

        // When
        EventOutcome model = mapper.dtoToModel(dto);

        // Then
        assertNotNull(model);
        assertEquals(dto.eventId(), model.getEventId());
        assertEquals(dto.eventName(), model.getEventName());
        assertEquals(dto.eventWinnerId(), model.getEventWinnerId());
    }

    @Test
    void entityToDto_shouldMapCorrectly() {
        // Given
        EventOutcomeEntity entity = new EventOutcomeEntity("event1", "Test Event", "team1");

        // When
        EventOutcomeDto dto = mapper.entityToDto(entity);

        // Then
        assertNotNull(dto);
        assertEquals(entity.getEventId(), dto.eventId());
        assertEquals(entity.getEventName(), dto.eventName());
        assertEquals(entity.getEventWinnerId(), dto.eventWinnerId());
    }

    @Test
    void dtoToEntity_shouldMapCorrectly() {
        // Given
        EventOutcomeDto dto = new EventOutcomeDto("event1", "Test Event", "team1");

        // When
        EventOutcomeEntity entity = mapper.dtoToEntity(dto);

        // Then
        assertNotNull(entity);
        assertEquals(dto.eventId(), entity.getEventId());
        assertEquals(dto.eventName(), entity.getEventName());
        assertEquals(dto.eventWinnerId(), entity.getEventWinnerId());
    }

    @Test
    void entitiesToModels_shouldMapCorrectly() {
        // Given
        List<EventOutcomeEntity> entities = Arrays.asList(new EventOutcomeEntity("event1", "Test Event 1", "team1"), new EventOutcomeEntity("event2", "Test Event 2", "team2"));

        // When
        List<EventOutcome> models = mapper.entitiesToModels(entities);

        // Then
        assertNotNull(models);
        assertEquals(2, models.size());
        assertEquals(entities.get(0).getEventId(), models.get(0).getEventId());
        assertEquals(entities.get(1).getEventId(), models.get(1).getEventId());
    }
}