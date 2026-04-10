package org.example.springbootservices.model.aventura;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.example.springbootservices.model.audit.Organization;
import org.example.springbootservices.model.audit.User;
import org.example.springbootservices.model.aventura.enums.AdventurerClass;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "aventureiros",schema = "aventura")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Adventurer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "organizacao_id",nullable = false)
    private Organization organization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id",nullable = false)
    private User user;

    @OneToMany(
            mappedBy = "adventurer",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Participation> participations = new ArrayList<>();

    @OneToOne(
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "companheiro_id",unique = true)
    private Companion companion;

    @Column(name = "nome",nullable = false, length = 120)
    private String name;

    @Column(name = "classe",nullable = false)
    private AdventurerClass adventurerClass;

    @Column(name = "nivel",nullable = false)
    private Integer level;

    @Column(name = "ativo",nullable = false)
    private Boolean active;

    @Column(name = "data_criacao",nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "data_atualizacao",nullable = false)
    private LocalDateTime updatedAt;

    public Adventurer(Organization organization, User user, String name, AdventurerClass adventurerClass, Integer level) {
        this.organization = organization;
        this.user = user;
        this.name = name;
        this.adventurerClass = adventurerClass;
        this.level = level;
        this.active = true;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
