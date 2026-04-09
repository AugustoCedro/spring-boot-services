package org.example.springbootservices.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public record CompanionRequestDTO(
        @NotBlank
        String name,
        @NotBlank
        String specie,
        @PositiveOrZero
        @Max(100)
        Integer loyalty
) {
}
