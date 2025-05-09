// BetController.java
package com.sportygroup.api;

import com.sportygroup.domain.dto.BetDto;
import com.sportygroup.domain.entity.BetEntity;
import com.sportygroup.mapper.BetMapper;
import com.sportygroup.repository.BetRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/bets")
@RequiredArgsConstructor
@Tag(name = "Bets", description = "Bets API")
public class BetController {

    private final BetRepository betRepository;
    private final BetMapper betMapper;

    @Operation(
            summary = "Get all bets",
            description = "Retrieves all available bets from the database"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Bets successfully retrieved",
                    content = {@Content(schema = @Schema(implementation = BetDto.class))})
    })
    @GetMapping
    public ResponseEntity<List<BetDto>> getAllBets() {
        List<BetEntity> betEntities = betRepository.findAll();
        List<BetDto> betDtos = betMapper.entitiesToDtos(betEntities);
        return ResponseEntity.ok(betDtos);
    }

    @Operation(
            summary = "Get bet by ID",
            description = "Retrieves a specific bet by its ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Bet successfully retrieved",
                    content = {@Content(schema = @Schema(implementation = BetDto.class))}),
            @ApiResponse(responseCode = "404", description = "Bet not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<BetDto> getBetById(@PathVariable String id) {
        Optional<BetEntity> betEntity = betRepository.findById(id);
        if (betEntity.isPresent()) {
            BetDto betDto = betMapper.entityToDto(betEntity.get());
            return ResponseEntity.ok(betDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Get bets by event ID",
            description = "Retrieves all bets related to a specific event"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Bets successfully retrieved",
                    content = {@Content(schema = @Schema(implementation = BetDto.class))})
    })
    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<BetDto>> getBetsByEventId(@PathVariable String eventId) {
        List<BetEntity> betEntities = betRepository.findByEventId(eventId);
        List<BetDto> betDtos = betMapper.entitiesToDtos(betEntities);
        return ResponseEntity.ok(betDtos);
    }
}