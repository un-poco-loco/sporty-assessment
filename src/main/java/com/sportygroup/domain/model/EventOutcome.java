// EventOutcome.java
package com.sportygroup.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventOutcome {
    private String eventId;
    private String eventName;
    private String eventWinnerId;
}