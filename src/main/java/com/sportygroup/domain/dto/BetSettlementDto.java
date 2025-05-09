// BetSettlementDto.java
package com.sportygroup.domain.dto;

import java.math.BigDecimal;

public record BetSettlementDto(
        String betId,
        String userId,
        String eventId,
        boolean winner,
        BigDecimal betAmount,
        BigDecimal payoutAmount
) {
}