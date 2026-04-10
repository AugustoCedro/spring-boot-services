package org.example.springbootservices.dto;


import org.example.springbootservices.model.aventura.enums.AdventurerClass;

import java.time.LocalDateTime;

public record AdventurerDetailsResponseDTO(
        Long id,
        OrganizationResponseDTO organization,
        String name,
        AdventurerClass adventurerClass,
        Integer level,
        Boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        CompanionResponseDTO companion,
        Integer totalMissions,
        Integer totalReward,
        MissionResponseDTO lastMission
) {




}
