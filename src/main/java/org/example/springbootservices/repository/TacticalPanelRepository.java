package org.example.springbootservices.repository;

import org.example.springbootservices.model.operacoes.TacticalPanel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.time.LocalDateTime;
import java.util.List;

public interface TacticalPanelRepository extends JpaRepository<TacticalPanel,Long> {

    @Query("""
    SELECT m 
    FROM TacticalPanel m
    WHERE m.updatedAt > :date
    ORDER BY m.readinessIndex DESC
    LIMIT 10
    """)
    List<TacticalPanel> findTop10Recent(@Param("date") LocalDateTime date);
}
