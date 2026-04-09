package org.example.springbootservices.model.audit;

import jakarta.persistence.*;
import lombok.*;
import org.example.springbootservices.model.aventura.Adventurer;
import org.example.springbootservices.model.aventura.Mission;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "organizacoes",schema = "audit")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", unique = true, nullable = false)
    private String name;

    @Column(name = "ativo", nullable = false)
    private Boolean active;

    @Column(name = "created_at", nullable = false)
    LocalDateTime createdAt;

    @OneToMany(
            mappedBy = "organization",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<User> userList = new ArrayList<>();

    @OneToMany(
            mappedBy = "organization",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ApiKey> apiKeyList = new ArrayList<>();

    @OneToMany(
            mappedBy = "organization",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<AuditEntry> auditEntryList = new ArrayList<>();

    @OneToMany(
            mappedBy = "organization",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Role> roleList = new ArrayList<>();

    @OneToMany(
            mappedBy = "organization",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Adventurer> adventurerList = new ArrayList<>();

    @OneToMany(
            mappedBy = "organization",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Mission> missionList = new ArrayList<>();

}
