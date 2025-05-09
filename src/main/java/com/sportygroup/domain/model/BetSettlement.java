// BetSettlement.java
package com.sportygroup.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BetSettlement {
    private String betId;
    private String userId;
    private String eventId;
    private boolean winner;
    private BigDecimal betAmount;
    private BigDecimal payoutAmount;
}