// EventOutcomeEntity.java
package com.sportygroup.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "event_outcomes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventOutcomeEntity {
    @Id
    private String eventId;
    private String eventName;
    private String eventWinnerId;
}

