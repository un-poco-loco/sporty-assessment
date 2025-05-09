// BetRepository.java
package com.sportygroup.repository;

import com.sportygroup.domain.entity.BetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BetRepository extends JpaRepository<BetEntity, String> {
    List<BetEntity> findByEventId(String eventId);
}

