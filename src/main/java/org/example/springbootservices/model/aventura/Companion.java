package org.example.springbootservices.model.aventura;

import jakarta.persistence.*;
import lombok.*;
import org.example.springbootservices.model.aventura.enums.Specie;

@Entity
@Table(name = "companheiros",schema = "aventura")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Companion {

    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private Adventurer adventurer;

    @Column(name = "nome",nullable = false, length = 120)
    private String nome;

    @Column(name = "especie",nullable = false)
    private Specie specie;

    @Column(name = "indice_lealdade",nullable = false)
    private Integer loyalty;
}
