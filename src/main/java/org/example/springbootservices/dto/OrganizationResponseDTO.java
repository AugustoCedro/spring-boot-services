package org.example.springbootservices.dto;

public record OrganizationResponseDTO(
        Long id,
        String name,
        Boolean active
) {
}
