package org.example.springbootservices.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.validator.constraints.Length;

public record CompanionRequestDTO(
        @NotBlank
        @Length(max = 120)
        String name,
        @NotBlank
        String specie,
        @PositiveOrZero
        @Max(100)
        Integer loyalty
) {
}
