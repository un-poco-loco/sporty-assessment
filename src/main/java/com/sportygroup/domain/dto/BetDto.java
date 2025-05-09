// BetDto.java
package com.sportygroup.domain.dto;

import java.math.BigDecimal;

public record BetDto(String betId,
                     String userId,
                     String eventId,
                     String eventMarketId,
                     String eventWinnerId,
                     BigDecimal betAmount
) {
}