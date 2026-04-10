package org.example.springbootservices.model.aventura;

import jakarta.persistence.*;
import lombok.*;
import org.example.springbootservices.model.aventura.enums.Role;


import java.time.LocalDateTime;

@Entity
@Table(
        name = "participacoes",
        schema = "aventura",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_missao_aventureiro",
                        columnNames = {"missao_id", "aventureiro_id"}
                )
        }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Participation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "missao_id", nullable = false)
    private Mission mission;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aventureiro_id", nullable = false)
    private Adventurer adventurer;

    @Enumerated(EnumType.STRING)
    @Column(name = "papel_missao",nullable = false)
    private Role role;

    @Column(name = "recompensa_ouro")
    private Integer rewardInGold;

    @Column(name = "destaque",nullable = false)
    private Boolean mvp;

    @Column(name = "data_registro",nullable = false)
    private LocalDateTime registerDate;

    public Participation(Mission mission, Adventurer adventurer, Role role, Integer rewardInGold, Boolean mvp) {
        this.mission = mission;
        this.adventurer = adventurer;
        this.role = role;
        this.rewardInGold = rewardInGold;
        this.mvp = mvp;
        this.registerDate = LocalDateTime.now();
    }
}
