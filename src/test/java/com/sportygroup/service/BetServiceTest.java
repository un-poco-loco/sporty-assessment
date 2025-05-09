// BetServiceTest.java
package com.sportygroup.service;

import com.sportygroup.domain.entity.BetEntity;
import com.sportygroup.domain.model.Bet;
import com.sportygroup.domain.model.EventOutcome;
import com.sportygroup.mapper.BetMapper;
import com.sportygroup.repository.BetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BetServiceTest {

    @Mock
    private BetRepository betRepository;

    @Mock
    private BetMapper betMapper;

    @InjectMocks
    private BetService betService;

    private EventOutcome testEventOutcome;
    private List<BetEntity> testBetEntities;
    private List<Bet> testBets;

    @BeforeEach
    void setUp() {
        testEventOutcome = new EventOutcome("event1", "Test Event", "team1");

        testBetEntities = Arrays.asList(
                new BetEntity("bet1", "user1", "event1", "market1", "team1", new BigDecimal("100.00")),
                new BetEntity("bet2", "user2", "event1", "market1", "team2", new BigDecimal("150.00"))
        );

        testBets = Arrays.asList(
                new Bet("bet1", "user1", "event1", "market1", "team1", new BigDecimal("100.00")),
                new Bet("bet2", "user2", "event1", "market1", "team2", new BigDecimal("150.00"))
        );

        when(betRepository.findByEventId("event1")).thenReturn(testBetEntities);
        when(betMapper.entitiesToModels(testBetEntities)).thenReturn(testBets);
    }

    @Test
    void findBetsForSettlement_ShouldReturnBetsForEvent() {
        // When
        List<Bet> result = betService.findBetsForSettlement(testEventOutcome);

        // Then
        assertEquals(2, result.size());
        assertEquals(testBets, result);
        verify(betRepository, times(1)).findByEventId("event1");
        verify(betMapper, times(1)).entitiesToModels(testBetEntities);
    }
}