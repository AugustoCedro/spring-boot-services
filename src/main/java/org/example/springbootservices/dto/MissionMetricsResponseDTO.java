package org.example.springbootservices.dto;

import org.example.springbootservices.model.aventura.enums.DangerLevel;
import org.example.springbootservices.model.aventura.enums.MissionStatus;

public record MissionMetricsResponseDTO(
        String title,
        MissionStatus status,
        DangerLevel dangerLevel,
        Integer totalParticipants,
        Integer totalRewardInGold
) {
}
