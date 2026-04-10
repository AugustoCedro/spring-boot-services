package org.example.springbootservices.repository;

import org.example.springbootservices.model.aventura.Participation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParticipationRepository extends JpaRepository<Participation,Long> {
    Optional<Participation> findTopByAdventurerIdOrderByMissionCreatedAtDesc(Long id);
}
