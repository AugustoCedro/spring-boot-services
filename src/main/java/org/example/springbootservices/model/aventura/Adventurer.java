package org.example.springbootservices.model.aventura;

import jakarta.persistence.*;
import lombok.*;
import org.example.springbootservices.model.audit.Organization;
import org.example.springbootservices.model.audit.User;
import org.example.springbootservices.model.aventura.enums.AdventurerClass;

import java.time.LocalDateTime;

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
    @JoinColumn(name = "organizacao_id",nullable = false)
    private Organization organization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id",nullable = false)
    private User user;

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




}
