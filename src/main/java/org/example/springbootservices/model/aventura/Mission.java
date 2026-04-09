package org.example.springbootservices.model.aventura;

import jakarta.persistence.*;
import lombok.*;
import org.example.springbootservices.model.audit.Organization;
import org.example.springbootservices.model.aventura.enums.DangerLevel;
import org.example.springbootservices.model.aventura.enums.MissionStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "missoes",schema = "aventura")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Mission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizacao_id",nullable = false)
    private Organization organization;

    @Column(name = "titulo",nullable = false, length = 150)
    private String title;

    @Column(name = "nivel_perigo",nullable = false)
    private DangerLevel dangerLevel;

    @Column(name = "status",nullable = false)
    private MissionStatus status;

    @Column(name = "data_criacao",nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "data_inicio")
    private LocalDateTime startedAt;

    @Column(name = "data_termino")
    private LocalDateTime finishedAt;

}
