package org.example.springbootservices.model.audit;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_entries",schema = "audit")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AuditEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "audit_entries_id_seq",
            sequenceName = "audit_entries_id_seq",
            allocationSize = 1
    )
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizacao_id",nullable = false)
    private Organization organization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actor_user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actor_api_key_id")
    private ApiKey apiKey;

    @Column(nullable = false)
    private String action;

    @Column(name = "entity_schema", nullable = false)
    private String entitySchema;

    @Column(name = "entity_name", nullable = false)
    private String entityName;

    @Column(name = "entity_id")
    private String entityId;

    @Column(name = "occurred_at", nullable = false)
    private LocalDateTime occurredAt;

    @Column(nullable = false, columnDefinition = "inet")
    private String ip;

    @Column(name = "user_agent",nullable = false)
    private String userAgent;

    @Column(nullable = false, columnDefinition = "jsonb")
    private String diff;

    @Column(name = "metadata", columnDefinition = "jsonb")
    private String metaData;

    @Column(nullable = false)
    private Boolean success;
}
