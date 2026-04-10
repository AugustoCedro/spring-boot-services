package org.example.springbootservices.mapper;

import lombok.AllArgsConstructor;
import org.example.springbootservices.dto.AdventurerDetailsResponseDTO;
import org.example.springbootservices.dto.AdventurerResponseDTO;
import org.example.springbootservices.dto.AdventurerSearchResponseDTO;
import org.example.springbootservices.dto.MissionResponseDTO;
import org.example.springbootservices.model.aventura.Adventurer;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AdventurerMapper {

    private OrganizationMapper organizationMapper;
    private CompanionMapper companionMapper;

    public AdventurerResponseDTO toAdventurerResponseDTO(Adventurer adventurer){
        return new AdventurerResponseDTO(
                adventurer.getId(),
                adventurer.getName(),
                adventurer.getAdventurerClass(),
                adventurer.getLevel(),
                adventurer.getActive()
        );
    }

    public AdventurerSearchResponseDTO toAdventurerSearchResponseDTO(Adventurer adventurer){
        return new AdventurerSearchResponseDTO(
                adventurer.getId(),
                adventurer.getName(),
                adventurer.getLevel(),
                adventurer.getAdventurerClass(),
                adventurer.getActive(),
                organizationMapper.toOrganizationResponseDTO(adventurer.getOrganization()),
                adventurer.getCompanion() != null
                        ? companionMapper.toCompanionResponseDTO(adventurer.getCompanion())
                        : null
        );
    }

    public AdventurerDetailsResponseDTO toAdventurerDetailsResponseDTO(Adventurer adventurer, MissionResponseDTO mission){
        return new AdventurerDetailsResponseDTO(
                adventurer.getId(),
                organizationMapper.toOrganizationResponseDTO(adventurer.getOrganization()),
                adventurer.getName(),
                adventurer.getAdventurerClass(),
                adventurer.getLevel(),
                adventurer.getActive(),
                adventurer.getCreatedAt(),
                adventurer.getUpdatedAt(),
                adventurer.getCompanion() != null
                        ? companionMapper.toCompanionResponseDTO(adventurer.getCompanion())
                        : null,
                adventurer.getParticipations().size(),
                adventurer.getParticipations().stream()
                        .reduce(0, (acc, p) -> acc + p.getRewardInGold(),Integer::sum),
                mission
        );
    }
}
