package org.example.springbootservices.model.audit;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.example.springbootservices.model.aventura.Adventurer;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios",
        schema = "audit",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_org_email",
                        columnNames = {"organizacao_id","email"}
                )
        }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "organizacao_id",nullable = false)
    private Organization organization;

    @Column(name = "nome", nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(name = "senha_hash", nullable = false)
    private String hashPassword;

    @Column(nullable = false)
    private String status;

    @Column(name = "ultimo_login_em")
    private LocalDateTime lastLoginAt;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(
            mappedBy = "user"
    )
    private List<AuditEntry> auditEntryList = new ArrayList<>();

    @OneToMany(
            mappedBy = "user"
    )
    @JsonIgnore
    private List<UserRole> userRoleList = new ArrayList<>();

    @OneToMany(
            mappedBy = "user"
    )
    @JsonIgnore
    private List<Adventurer> adventurerList = new ArrayList<>();

}
