// BetSettlementServiceTest.java
package com.sportygroup.service;

import com.sportygroup.domain.model.Bet;
import com.sportygroup.domain.model.BetSettlement;
import com.sportygroup.domain.model.EventOutcome;
import com.sportygroup.events.RocketMQProducerMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BetSettlementServiceTest {

    @Mock
    private BetService betService;

    @Mock
    private RocketMQProducerMock rocketMQProducerMock;

    @InjectMocks
    private BetSettlementService betSettlementService;

    @Captor
    private ArgumentCaptor<BetSettlement> betSettlementCaptor;

    private EventOutcome testEventOutcome;

    @BeforeEach
    void setUp() {
        testEventOutcome = new EventOutcome("event1", "Test Event", "team1");

        List<Bet> testBets = Arrays.asList(
                new Bet("bet1", "user1", "event1", "market1", "team1", new BigDecimal("100.00")),
                new Bet("bet2", "user2", "event1", "market1", "team2", new BigDecimal("150.00"))
        );

        when(betService.findBetsForSettlement(testEventOutcome)).thenReturn(testBets);
    }

    @Test
    void processEventOutcome_ShouldSettleBets() {
        // When
        betSettlementService.processEventOutcome(testEventOutcome);

        // Then
        verify(betService, times(1)).findBetsForSettlement(testEventOutcome);
        verify(rocketMQProducerMock, times(2)).sendBetSettlement(betSettlementCaptor.capture());

        List<BetSettlement> capturedSettlements = betSettlementCaptor.getAllValues();
        assertEquals(2, capturedSettlements.size());

        // First bet should be a winner (team1 bet on team1 winner)
        assertTrue(capturedSettlements.get(0).isWinner());
        assertEquals(new BigDecimal("200.00"), capturedSettlements.get(0).getPayoutAmount());

        // Second bet should be a loser (team2 bet on team1 winner)
        assertFalse(capturedSettlements.get(1).isWinner());
        assertEquals(BigDecimal.ZERO, capturedSettlements.get(1).getPayoutAmount());
    }
}