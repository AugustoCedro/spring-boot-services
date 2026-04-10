package org.example.springbootservices.dto;

import org.example.springbootservices.model.aventura.enums.AdventurerClass;

public record AdventurerResponseDTO(
        Long id,
        String name,
        AdventurerClass adventurerClass,
        Integer level,
        Boolean active
) {
}