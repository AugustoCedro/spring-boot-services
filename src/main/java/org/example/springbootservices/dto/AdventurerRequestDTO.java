package org.example.springbootservices.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AdventurerRequestDTO(
        @NotBlank
        String name,
        @NotBlank
        String adventurerClass,
        @NotNull
        @Positive
        Integer level
) {
}
