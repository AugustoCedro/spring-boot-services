package org.example.springbootservices.dto;

import org.example.springbootservices.model.aventura.enums.Role;

public record ParticipationDTO(
        Long id,
        AdventurerResponseDTO adventurer,
        Role role,
        Integer rewardInGold,
        Boolean mvp
) {
}
