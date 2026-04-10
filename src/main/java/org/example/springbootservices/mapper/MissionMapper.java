package org.example.springbootservices.mapper;

import org.example.springbootservices.dto.*;
import org.example.springbootservices.model.aventura.Mission;
import org.example.springbootservices.model.aventura.Participation;
import org.springframework.stereotype.Component;

@Component
public class MissionMapper {

    public ParticipationDTO toParticipationDTO(Participation participation, AdventurerResponseDTO adventurerResponseDTO) {
        return new ParticipationDTO(
                participation.getId(),
                adventurerResponseDTO,
                participation.getRole(),
                participation.getRewardInGold(),
                participation.getMvp()
        );
    }

    public MissionDetailsResponseDTO toMissionDetailsResponseDTO(Mission mission, AdventurerMapper adventurerMapper) {
        return new MissionDetailsResponseDTO(
                mission.getId(),
                mission.getOrganization().getName(),
                mission.getTitle(),
                mission.getDangerLevel(),
                mission.getStatus(),
                mission.getCreatedAt(),
                mission.getStartedAt(),
                mission.getFinishedAt(),
                mission.getParticipations()
                        .stream()
                        .map(p -> toParticipationDTO(p,adventurerMapper.toAdventurerResponseDTO(p.getAdventurer())))
                        .toList()
        );
    }

    public MissionResponseDTO toMissionResponseDTO(Mission mission){
        return new MissionResponseDTO(
                mission.getId(),
                mission.getOrganization().getName(),
                mission.getTitle(),
                mission.getDangerLevel(),
                mission.getStatus(),
                mission.getCreatedAt(),
                mission.getStartedAt(),
                mission.getFinishedAt()
        );
    }

    public MissionMetricsResponseDTO toMissionMetricsResponseDTO(Mission mission){
        return new MissionMetricsResponseDTO(
                mission.getTitle(),
                mission.getStatus(),
                mission.getDangerLevel(),
                mission.getParticipations().size(),
                mission.getParticipations().stream().map(Participation::getRewardInGold).reduce(0,Integer::sum)
        );
    }

}
