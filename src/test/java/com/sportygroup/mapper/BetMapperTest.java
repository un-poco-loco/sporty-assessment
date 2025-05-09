// BetMapperTest.java
package com.sportygroup.mapper;

import com.sportygroup.domain.dto.BetDto;
import com.sportygroup.domain.entity.BetEntity;
import com.sportygroup.domain.model.Bet;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BetMapperTest {

    private final BetMapper mapper = Mappers.getMapper(BetMapper.class);

    @Test
    void entityToModel_shouldMapCorrectly() {
        // Given
        BetEntity entity = new BetEntity("bet1", "user1", "event1", "market1", "team1", new BigDecimal("100.00"));

        // When
        Bet model = mapper.entityToModel(entity);

        // Then
        assertNotNull(model);
        assertEquals(entity.getBetId(), model.getBetId());
        assertEquals(entity.getUserId(), model.getUserId());
        assertEquals(entity.getEventId(), model.getEventId());
        assertEquals(entity.getEventMarketId(), model.getEventMarketId());
        assertEquals(entity.getEventWinnerId(), model.getEventWinnerId());
        assertEquals(entity.getBetAmount(), model.getBetAmount());
    }

    @Test
    void modelToEntity_shouldMapCorrectly() {
        // Given
        Bet model = new Bet("bet1", "user1", "event1", "market1", "team1", new BigDecimal("100.00"));

        // When
        BetEntity entity = mapper.modelToEntity(model);

        // Then
        assertNotNull(entity);
        assertEquals(model.getBetId(), entity.getBetId());
        assertEquals(model.getUserId(), entity.getUserId());
        assertEquals(model.getEventId(), entity.getEventId());
        assertEquals(model.getEventMarketId(), entity.getEventMarketId());
        assertEquals(model.getEventWinnerId(), entity.getEventWinnerId());
        assertEquals(model.getBetAmount(), entity.getBetAmount());
    }

    @Test
    void modelToDto_shouldMapCorrectly() {
        // Given
        Bet model = new Bet("bet1", "user1", "event1", "market1", "team1", new BigDecimal("100.00"));

        // When
        BetDto dto = mapper.modelToDto(model);

        // Then
        assertNotNull(dto);
        assertEquals(model.getBetId(), dto.betId());
        assertEquals(model.getUserId(), dto.userId());
        assertEquals(model.getEventId(), dto.eventId());
        assertEquals(model.getEventMarketId(), dto.eventMarketId());
        assertEquals(model.getEventWinnerId(), dto.eventWinnerId());
        assertEquals(model.getBetAmount(), dto.betAmount());
    }

    @Test
    void dtoToModel_shouldMapCorrectly() {
        // Given
        BetDto dto = new BetDto("bet1", "user1", "event1", "market1", "team1", new BigDecimal("100.00"));

        // When
        Bet model = mapper.dtoToModel(dto);

        // Then
        assertNotNull(model);
        assertEquals(dto.betId(), model.getBetId());
        assertEquals(dto.userId(), model.getUserId());
        assertEquals(dto.eventId(), model.getEventId());
        assertEquals(dto.eventMarketId(), model.getEventMarketId());
        assertEquals(dto.eventWinnerId(), model.getEventWinnerId());
        assertEquals(dto.betAmount(), model.getBetAmount());
    }

    @Test
    void entityToDto_shouldMapCorrectly() {
        // Given
        BetEntity entity = new BetEntity("bet1", "user1", "event1", "market1", "team1", new BigDecimal("100.00"));

        // When
        BetDto dto = mapper.entityToDto(entity);

        // Then
        assertNotNull(dto);
        assertEquals(entity.getBetId(), dto.betId());
        assertEquals(entity.getUserId(), dto.userId());
        assertEquals(entity.getEventId(), dto.eventId());
        assertEquals(entity.getEventMarketId(), dto.eventMarketId());
        assertEquals(entity.getEventWinnerId(), dto.eventWinnerId());
        assertEquals(entity.getBetAmount(), dto.betAmount());
    }

    @Test
    void dtoToEntity_shouldMapCorrectly() {
        // Given
        BetDto dto = new BetDto("bet1", "user1", "event1", "market1", "team1", new BigDecimal("100.00"));

        // When
        BetEntity entity = mapper.dtoToEntity(dto);

        // Then
        assertNotNull(entity);
        assertEquals(dto.betId(), entity.getBetId());
        assertEquals(dto.userId(), entity.getUserId());
        assertEquals(dto.eventId(), entity.getEventId());
        assertEquals(dto.eventMarketId(), entity.getEventMarketId());
        assertEquals(dto.eventWinnerId(), entity.getEventWinnerId());
        assertEquals(dto.betAmount(), entity.getBetAmount());
    }

    @Test
    void entitiesToModels_shouldMapCorrectly() {
        // Given
        List<BetEntity> entities = Arrays.asList(
                new BetEntity("bet1", "user1", "event1", "market1", "team1", new BigDecimal("100.00")),
                new BetEntity("bet2", "user2", "event1", "market1", "team2", new BigDecimal("150.00"))
        );

        // When
        List<Bet> models = mapper.entitiesToModels(entities);
        // Then
        assertNotNull(models);
        assertEquals(2, models.size());
        assertEquals(entities.get(0).getBetId(), models.get(0).getBetId());
        assertEquals(entities.get(1).getBetId(), models.get(1).getBetId());
    }

    @Test
    void modelsToEntities_shouldMapCorrectly() {
        // Given
        List<Bet> models = Arrays.asList(
                new Bet("bet1", "user1", "event1", "market1", "team1", new BigDecimal("100.00")),
                new Bet("bet2", "user2", "event1", "market1", "team2", new BigDecimal("150.00"))
        );

        // When
        List<BetEntity> entities = mapper.modelsToEntities(models);

        // Then
        assertNotNull(entities);
        assertEquals(2, entities.size());
        assertEquals(models.get(0).getBetId(), entities.get(0).getBetId());
        assertEquals(models.get(1).getBetId(), entities.get(1).getBetId());
    }

}