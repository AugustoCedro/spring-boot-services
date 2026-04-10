package org.example.springbootservices.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

public record AdventurerRequestDTO(

        @NotBlank
        String organization,
        @NotBlank
        @Length(max = 150)
        String user,
        @NotBlank
        String name,
        @NotBlank
        String adventurerClass,
        @NotNull
        @Positive
        Integer level
) {
}
