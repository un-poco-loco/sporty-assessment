
// BetSettlementMapperTest.java
package com.sportygroup.mapper;

import com.sportygroup.domain.dto.BetSettlementDto;
import com.sportygroup.domain.model.BetSettlement;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BetSettlementMapperTest {

    private final BetSettlementMapper mapper = Mappers.getMapper(BetSettlementMapper.class);

    @Test
    void modelToDto_shouldMapCorrectly() {
        // Given
        BetSettlement model = new BetSettlement("bet1", "user1", "event1", true, new BigDecimal("100.00"), new BigDecimal("200.00"));

        // When
        BetSettlementDto dto = mapper.modelToDto(model);

        // Then
        assertNotNull(dto);
        assertEquals(model.getBetId(), dto.betId());
        assertEquals(model.getUserId(), dto.userId());
        assertEquals(model.getEventId(), dto.eventId());
        assertEquals(model.isWinner(), dto.winner());
        assertEquals(model.getBetAmount(), dto.betAmount());
        assertEquals(model.getPayoutAmount(), dto.payoutAmount());
    }

    @Test
    void dtoToModel_shouldMapCorrectly() {
        // Given
        BetSettlementDto dto = new BetSettlementDto("bet1", "user1", "event1", true, new BigDecimal("100.00"), new BigDecimal("200.00"));

        // When
        BetSettlement model = mapper.dtoToModel(dto);

        // Then
        assertNotNull(model);
        assertEquals(dto.betId(), model.getBetId());
        assertEquals(dto.userId(), model.getUserId());
        assertEquals(dto.eventId(), model.getEventId());
        assertEquals(dto.winner(), model.isWinner());
        assertEquals(dto.betAmount(), model.getBetAmount());
        assertEquals(dto.payoutAmount(), model.getPayoutAmount());
    }

    @Test
    void modelsToDtos_shouldMapCorrectly() {
        // Given
        List<BetSettlement> models = Arrays.asList(new BetSettlement("bet1", "user1", "event1", true, new BigDecimal("100.00"), new BigDecimal("200.00")), new BetSettlement("bet2", "user2", "event1", false, new BigDecimal("150.00"), BigDecimal.ZERO));

        // When
        List<BetSettlementDto> dtos = mapper.modelsToDtos(models);

        // Then
        assertNotNull(dtos);
        assertEquals(2, dtos.size());
        assertEquals(models.get(0).getBetId(), dtos.get(0).betId());
        assertEquals(models.get(1).getBetId(), dtos.get(1).betId());
    }
}