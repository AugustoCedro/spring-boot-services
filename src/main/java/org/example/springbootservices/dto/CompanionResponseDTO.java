package org.example.springbootservices.dto;

import org.example.springbootservices.model.aventura.enums.Specie;

public record CompanionResponseDTO(
        Long id,
        String name,
        Specie specie,
        Integer loyalty
) {
}
