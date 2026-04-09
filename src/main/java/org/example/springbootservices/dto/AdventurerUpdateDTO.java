package org.example.springbootservices.dto;


import jakarta.validation.constraints.Positive;

public record AdventurerUpdateDTO(
        String name,
        String adventurerClass,
        @Positive
        Integer level
) {
}
