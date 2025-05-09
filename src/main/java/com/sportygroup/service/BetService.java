// BetService.java
package com.sportygroup.service;

import com.sportygroup.domain.entity.BetEntity;
import com.sportygroup.domain.model.Bet;
import com.sportygroup.domain.model.EventOutcome;
import com.sportygroup.mapper.BetMapper;
import com.sportygroup.repository.BetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BetService {
    private final BetRepository betRepository;
    private final BetMapper betMapper;

    @Transactional(readOnly = true)
    public List<Bet> findBetsForSettlement(EventOutcome eventOutcome) {
        log.info("Finding bets for event: {}", eventOutcome.getEventId());

        // Fetch entities from database
        List<BetEntity> betEntities = betRepository.findByEventId(eventOutcome.getEventId());

        // Convert entities to models
        return betMapper.entitiesToModels(betEntities);
    }
}