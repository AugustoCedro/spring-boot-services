package org.example.springbootservices.repository;

import org.example.springbootservices.model.aventura.Mission;
import org.example.springbootservices.model.aventura.enums.DangerLevel;
import org.example.springbootservices.model.aventura.enums.MissionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface MissionRepository extends JpaRepository<Mission,Long> {
    @Query("""
    SELECT m FROM Mission m
    WHERE (:status IS NULL OR m.status = :status)
      AND (:dangerLevel IS NULL OR m.dangerLevel = :dangerLevel)
      AND (cast(:startDate as date) is null or m.createdAt >= :startDate)
      AND (cast(:endDate as date) is null or m.finishedAt <= :endDate)
    """)
    Page<Mission> findWithFilters(
            @Param("status") MissionStatus status,
            @Param("dangerLevel") DangerLevel dangerLevel,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageRequest
    );
}
