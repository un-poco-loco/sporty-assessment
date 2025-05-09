// EventOutcomeController.java
package com.sportygroup.api;

import com.sportygroup.domain.dto.EventOutcomeDto;
import com.sportygroup.domain.model.EventOutcome;
import com.sportygroup.mapper.EventOutcomeMapper;
import com.sportygroup.service.EventOutcomeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
@Tag(name = "Event Outcome", description = "Event Outcome API")
public class EventOutcomeController {
    private final EventOutcomeService eventOutcomeService;
    private final EventOutcomeMapper eventOutcomeMapper;

    @PostMapping("/outcome")
    public ResponseEntity<EventOutcomeDto> publishEventOutcome(@RequestBody EventOutcomeDto eventOutcomeDto) {
        log.info("Received request to publish event outcome: {}", eventOutcomeDto);

        // Convert DTO to model
        EventOutcome eventOutcome = eventOutcomeMapper.dtoToModel(eventOutcomeDto);

        // Process the model in the service
        EventOutcome publishedOutcome = eventOutcomeService.publishEventOutcome(eventOutcome);

        // Convert the result back to DTO
        EventOutcomeDto resultDto = eventOutcomeMapper.modelToDto(publishedOutcome);

        return new ResponseEntity<>(resultDto, HttpStatus.CREATED);
    }
}