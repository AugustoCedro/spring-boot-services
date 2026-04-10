package org.example.springbootservices.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.example.springbootservices.dto.*;
import org.example.springbootservices.exception.AdventurerAlreadyHasCompanionException;
import org.example.springbootservices.exception.InvalidEnumValueException;
import org.example.springbootservices.exception.ResourceNotFoundException;
import org.example.springbootservices.mapper.AdventurerMapper;
import org.example.springbootservices.mapper.MissionMapper;
import org.example.springbootservices.model.audit.Organization;
import org.example.springbootservices.model.audit.User;
import org.example.springbootservices.model.aventura.Adventurer;
import org.example.springbootservices.model.aventura.Companion;
import org.example.springbootservices.model.aventura.Mission;
import org.example.springbootservices.model.aventura.enums.AdventurerClass;
import org.example.springbootservices.model.aventura.enums.MissionStatus;
import org.example.springbootservices.model.aventura.enums.Specie;
import org.example.springbootservices.repository.AdventurerRepository;
import org.example.springbootservices.repository.CompanionRepository;
import org.example.springbootservices.repository.OrganizationRepository;
import org.example.springbootservices.util.Randomizer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AdventurerService {

    private AdventurerRepository adventurerRepository;
    private OrganizationRepository organizationRepository;
    private AdventurerMapper mapper;
    private CompanionRepository companionRepository;
    private MissionService missionService;

    public AdventurerSearchResponseDTO register(AdventurerRequestDTO dto){
        Organization organization = organizationRepository.findByName(dto.organization())
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found"));

        User user = organization.getUserList().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(dto.user()))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("User not found in this organization"));

        AdventurerClass adventurerClass = Arrays.stream(AdventurerClass.values())
                                            .filter(c -> c.name().equalsIgnoreCase(dto.adventurerClass()))
                                            .findFirst()
                                            .orElseThrow(() -> new InvalidEnumValueException("AdventurerClass","Invalid adventurerClass"));

        Adventurer adventurer = new Adventurer(organization,user, dto.name(), adventurerClass,dto.level());
        organization.getAdventurerList().add(adventurer);
        user.getAdventurerList().add(adventurer);
        user.setUpdatedAt(LocalDateTime.now());
        adventurerRepository.save(adventurer);

        return mapper.toAdventurerSearchResponseDTO(adventurer);
    }

    public Page<AdventurerResponseDTO> listAdventurersWithFilters(Boolean active, String adventurerClassRequest, Integer minLevel, int size, int page) {
        PageRequest pageRequest = PageRequest.of(page,size, Sort.by("level").ascending());

        AdventurerClass adventurerClass = adventurerClassRequest != null ?
                Arrays.stream(AdventurerClass.values())
                        .filter(c -> c.name().equalsIgnoreCase(adventurerClassRequest))
                        .findFirst()
                        .orElseThrow(() -> new InvalidEnumValueException("AdventurerClass","Invalid adventurerClass"))
                : null;

        Page<Adventurer> adventurers = adventurerRepository.findWithFilters(active, adventurerClass, minLevel,pageRequest);
        return adventurers.map(a -> mapper.toAdventurerResponseDTO(a));
    }

    public Page<AdventurerSearchResponseDTO> listAdventurersByName(String name, int size, int page) {
        PageRequest pageRequest = PageRequest.of(page,size,Sort.by("level").ascending());

        Page<Adventurer> adventurers = adventurerRepository.findByNameContainingIgnoreCase(name,pageRequest);

        return adventurers.map(a -> mapper.toAdventurerSearchResponseDTO(a));
    }

    public AdventurerDetailsResponseDTO getAdventurerById(Long id) {
        Adventurer adventurer = adventurerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Adventurer not found"));

        MissionResponseDTO lastMission = missionService.getAdventurerLastMission(adventurer);

        return mapper.toAdventurerDetailsResponseDTO(adventurer,lastMission);
    }

    public Page<AdventurerDetailsResponseDTO> listAdventurersRanking(String status, LocalDate startDate, LocalDate endDate, int size, int page) {
        LocalDateTime startDateTime = startDate != null ? startDate.atStartOfDay() : null;
        LocalDateTime endDateTime = endDate != null ? endDate.atStartOfDay() : null;

        MissionStatus missionStatus = status != null ? Arrays.stream(MissionStatus.values())
                .filter(m -> m.name().equalsIgnoreCase(status))
                .findFirst()
                .orElseThrow(() -> new InvalidEnumValueException("MissionStatus", "Invalid missionStatus"))
        : null;

        PageRequest pageRequest = PageRequest.of(page,size);
        Page<Adventurer> adventurers = adventurerRepository.findRanking(missionStatus,startDateTime,endDateTime,pageRequest);
        return adventurers.map(a -> mapper.toAdventurerDetailsResponseDTO(a,missionService.getAdventurerLastMission(a)));
    }

    public AdventurerSearchResponseDTO registerCompanion(Long adventurerId, CompanionRequestDTO dto) {
        Adventurer adventurer = adventurerRepository.findById(adventurerId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Optional.ofNullable(adventurer.getCompanion())
                .ifPresent(c -> {
                    throw new AdventurerAlreadyHasCompanionException("Adventurer '" + adventurer.getName() + "' already has a companion");
                });

        Specie specie = Arrays.stream(Specie.values())
                .filter(s -> s.name()
                        .equalsIgnoreCase(dto.specie()))
                .findFirst()
                .orElseThrow(() -> new InvalidEnumValueException("Specie","Invalid specie"));


        Companion companion = new Companion(adventurer,dto.name(),specie,Randomizer.generateRandomLoyalty());
        adventurer.setCompanion(companion);
        adventurer.setUpdatedAt(LocalDateTime.now());
        companionRepository.save(companion);

        return mapper.toAdventurerSearchResponseDTO(adventurer);
    }
}
