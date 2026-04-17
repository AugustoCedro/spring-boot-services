package org.example.springbootservices.model.audit;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
@EqualsAndHashCode
public class UserRoleId {
    private Long userId;
    private Long roleId;
}
