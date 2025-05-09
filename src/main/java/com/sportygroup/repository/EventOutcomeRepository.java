// EventOutcomeRepository.java
package com.sportygroup.repository;

import com.sportygroup.domain.entity.EventOutcomeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventOutcomeRepository extends JpaRepository<EventOutcomeEntity, String> {
}