package org.example.springbootservices.dto;

import org.example.springbootservices.model.aventura.enums.AdventurerClass;

public record AdventurerSearchResponseDTO(
        Long id,
        String name,
        Integer level,
        AdventurerClass adventurerClass,
        Boolean active,
        OrganizationResponseDTO organization,
        CompanionResponseDTO companion
) {
}
