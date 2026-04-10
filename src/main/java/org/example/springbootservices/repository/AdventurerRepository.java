package org.example.springbootservices.repository;

import org.example.springbootservices.model.aventura.Adventurer;
import org.example.springbootservices.model.aventura.enums.AdventurerClass;
import org.example.springbootservices.model.aventura.enums.MissionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public interface AdventurerRepository extends JpaRepository<Adventurer,Long> {
    @Query("""
        SELECT a FROM Adventurer a
        WHERE (:active IS NULL OR a.active = :active)
        AND   (:adventurerClass IS NULL OR a.adventurerClass = :adventurerClass)
        AND   (:minLevel IS NULL OR a.level >= :minLevel)
    """)
    Page<Adventurer> findWithFilters(
            @Param("active") Boolean active,
            @Param("adventurerClass") AdventurerClass adventurerClass,
            @Param("minLevel") Integer minLevel,
            Pageable pageable);
    Page<Adventurer> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Optional<Adventurer> findByIdAndOrganizationId(Long adventurerId, Long id);

    @Query("""
        SELECT a FROM Adventurer a
        LEFT JOIN a.participations p
        LEFT JOIN p.mission m
        WHERE (CAST(:status AS string) IS NULL OR m.status = :status)
          AND (CAST(:startDate AS date) IS NULL OR m.createdAt >= :startDate)
          AND (CAST(:endDate AS date) IS NULL OR m.finishedAt <= :endDate)
        GROUP BY a
        ORDER BY
           COUNT(p) DESC,
           SUM(p.rewardInGold) DESC,
           SUM(CASE WHEN p.mvp = true THEN 1 ELSE 0 END) DESC
    """)
    Page<Adventurer> findRanking(
            @Param("status") MissionStatus missionStatus,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageRequest
    );
}
