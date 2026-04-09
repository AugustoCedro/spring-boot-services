package org.example.springbootservices.model.audit;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "api_keys",
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
public class ApiKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizacao_id", nullable = false)
    private Organization organization;

    @Column(name = "nome",nullable = false)
    private String name;

    @Column(name = "key_hash",nullable = false)
    private String hashKey;

    @Column(name = "ativo",nullable = false)
    private Boolean active;

    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "last_used_at")
    private LocalDateTime lastUsedAt;

    @OneToMany(
            mappedBy = "apiKey"
    )
    private List<AuditEntry> auditEntryList = new ArrayList<>();
}
