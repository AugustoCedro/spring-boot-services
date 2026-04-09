package org.example.springbootservices.model.audit;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class UserRoleId {
    private Long userId;
    private Long roleId;
}
