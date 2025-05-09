// BetSettlementService.java
package com.sportygroup.service;

import com.sportygroup.domain.model.Bet;
import com.sportygroup.domain.model.BetSettlement;
import com.sportygroup.domain.model.EventOutcome;
import com.sportygroup.events.RocketMQProducerMock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BetSettlementService {
    private final BetService betService;
    private final RocketMQProducerMock rocketMQProducerMock;

    public void processEventOutcome(EventOutcome eventOutcome) {
        log.info("Processing event outcome for event: {}", eventOutcome.getEventId());
        List<Bet> betsToSettle = betService.findBetsForSettlement(eventOutcome);
        log.info("Found {} bets to settle", betsToSettle.size());

        for (Bet bet : betsToSettle) {
            boolean isWinner = bet.getEventWinnerId().equals(eventOutcome.getEventWinnerId());
            BigDecimal payoutAmount = calculatePayout(bet, isWinner);

            BetSettlement betSettlement = new BetSettlement(
                    bet.getBetId(),
                    bet.getUserId(),
                    bet.getEventId(),
                    isWinner,
                    bet.getBetAmount(),
                    payoutAmount
            );

            log.info("Settling bet: {}, Winner: {}, Payout: {}", bet.getBetId(), isWinner, payoutAmount);
            rocketMQProducerMock.sendBetSettlement(betSettlement);
        }
    }

    private BigDecimal calculatePayout(Bet bet, boolean isWinner) {
        // Simple payout calculation - in a real system this would be more complex
        if (isWinner) {
            // Winner gets 2x their bet
            return bet.getBetAmount().multiply(new BigDecimal("2"));
        } else {
            // Loser gets nothing
            return BigDecimal.ZERO;
        }
    }
}