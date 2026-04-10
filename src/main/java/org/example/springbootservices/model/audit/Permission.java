package org.example.springbootservices.model.audit;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "permissions", schema = "audit")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String code;

    @Column(name = "descricao",nullable = false)
    private String description;

    @ManyToMany(mappedBy = "permissionList")
    private List<Role> roleList = new ArrayList<>();

}
