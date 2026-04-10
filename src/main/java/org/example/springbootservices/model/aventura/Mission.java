package org.example.springbootservices.model.aventura;

import jakarta.persistence.*;
import lombok.*;
import org.example.springbootservices.model.audit.Organization;
import org.example.springbootservices.model.aventura.enums.DangerLevel;
import org.example.springbootservices.model.aventura.enums.MissionStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(
            mappedBy = "mission",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Participation> participations = new ArrayList<>();

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

    public Mission(Organization organization, String title, DangerLevel dangerLevel, MissionStatus status, LocalDateTime createdAt) {
        this.organization = organization;
        this.title = title;
        this.dangerLevel = dangerLevel;
        this.status = status;
        this.createdAt = createdAt;
        status.apply(this);
    }
}
