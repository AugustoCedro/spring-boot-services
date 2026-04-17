package org.example.springbootservices.model.audit;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "roles",
        schema = "audit",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_org_nome",
                        columnNames = {"organizacao_id","nome"}
                )
        }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "roles_id_seq",
            sequenceName = "roles_id_seq",
            allocationSize = 1
    )
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizacao_id",nullable = false)
    private Organization organization;

    @Column(name = "nome",nullable = false)
    private String name;

    @Column(name = "descricao",nullable = false)
    private String description;

    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;

    @ManyToMany
    @JoinTable(
            name = "role_permissions",
            schema = "audit",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private List<Permission> permissionList = new ArrayList<>();

    @OneToMany(
            mappedBy = "role"
    )
    private List<UserRole> userRoleList = new ArrayList<>();
}
