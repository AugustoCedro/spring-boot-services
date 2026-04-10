package org.example.springbootservices.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record MissionRequestDTO(
        @NotBlank
        String organization,
        @NotBlank
        @Length(max = 150)
        String title
) {
}
