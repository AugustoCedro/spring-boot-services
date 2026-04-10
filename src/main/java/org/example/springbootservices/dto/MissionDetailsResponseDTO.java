package org.example.springbootservices.dto;

import org.example.springbootservices.model.aventura.enums.DangerLevel;
import org.example.springbootservices.model.aventura.enums.MissionStatus;

import java.time.LocalDateTime;
import java.util.List;

public record MissionDetailsResponseDTO(
        Long id,
        String organization,
        String title,
        DangerLevel dangerLevel,
        MissionStatus status,
        LocalDateTime createdAt,
        LocalDateTime startedAt,
        LocalDateTime finishedAt,
        List<ParticipationDTO> participations
) {
}
