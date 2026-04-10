package org.example.springbootservices.service;

import jakarta.servlet.http.Part;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.example.springbootservices.dto.MissionDetailsResponseDTO;
import org.example.springbootservices.dto.MissionMetricsResponseDTO;
import org.example.springbootservices.dto.MissionRequestDTO;
import org.example.springbootservices.dto.MissionResponseDTO;
import org.example.springbootservices.exception.*;
import org.example.springbootservices.mapper.AdventurerMapper;
import org.example.springbootservices.mapper.MissionMapper;
import org.example.springbootservices.model.audit.Organization;
import org.example.springbootservices.model.aventura.Adventurer;
import org.example.springbootservices.model.aventura.Mission;
import org.example.springbootservices.model.aventura.Participation;
import org.example.springbootservices.model.aventura.enums.DangerLevel;
import org.example.springbootservices.model.aventura.enums.MissionStatus;
import org.example.springbootservices.repository.AdventurerRepository;
import org.example.springbootservices.repository.MissionRepository;
import org.example.springbootservices.repository.OrganizationRepository;
import org.example.springbootservices.repository.ParticipationRepository;
import org.example.springbootservices.util.Randomizer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

@Service
@AllArgsConstructor
public class MissionService {

    private OrganizationRepository organizationRepository;
    private MissionRepository missionRepository;
    private MissionMapper mapper;
    private AdventurerRepository adventurerRepository;
    private ParticipationRepository participationRepository;
    private AdventurerMapper adventurerMapper;

    public MissionDetailsResponseDTO registerMission(MissionRequestDTO dto){
        Organization organization = organizationRepository.findByName(dto.organization())
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found"));

        Mission mission = new Mission(
                organization,
                dto.title(),
                Randomizer.generateRandomDangerLevel(),
                Randomizer.generateRandomMissionStatus(),
                Randomizer.randomDateTime()
        );

        organization.getMissionList().add(mission);
        missionRepository.save(mission);
        return mapper.toMissionDetailsResponseDTO(mission,adventurerMapper);
    }

    public MissionDetailsResponseDTO registerParticipant(Long missionId, Long adventurerId) {
        Mission mission = missionRepository
                .findById(missionId)
                .orElseThrow(() -> new ResourceNotFoundException("Mission not found"));

        if(mission.getStatus() != MissionStatus.EM_ANDAMENTO && mission.getStatus() != MissionStatus.PLANEJADA){
            throw new MissionStatusException("Cannot add Adventurer in this mission status");
        }

        Adventurer adventurer = adventurerRepository
                .findByIdAndOrganizationId(adventurerId, mission.getOrganization().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Adventurer not found in this organization"));

        if (!adventurer.getActive()) {
            throw new InactiveAdventurerException("Adventurer is inactive");
        }

        mission.getParticipations()
                .stream()
                .filter(p -> p.getAdventurer().equals(adventurer))
                .findFirst()
                .ifPresent(p -> {
                    throw new AdventurerAlreadyInThisMissionException("Adventurer already in this mission");
                });

        Participation participation = new Participation(mission,adventurer, Randomizer.generateRandomMissionRole(),Randomizer.generateRandomReward(),Randomizer.generateRandomMvp());

        mission.getParticipations().add(participation);
        adventurer.getParticipations().add(participation);
        adventurer.setUpdatedAt(LocalDateTime.now());
        participationRepository.save(participation);

        return mapper.toMissionDetailsResponseDTO(mission,adventurerMapper);
    }

    public MissionResponseDTO getAdventurerLastMission(Adventurer adventurer) {
        return participationRepository.findTopByAdventurerIdOrderByMissionCreatedAtDesc(adventurer.getId())
                .map(p -> mapper.toMissionResponseDTO(p.getMission()))
                .orElse(null);

    }

    public Page<MissionResponseDTO> listMissionsWithFilters(String missionStatusRequest, String dangerLevelRequest, LocalDate startDate, LocalDate endDate, int size, int page) {
        LocalDateTime startDateTime = startDate != null ? startDate.atStartOfDay() : null;
        LocalDateTime endDateTime = endDate != null ? endDate.atStartOfDay() : null;

        DangerLevel dangerLevel = dangerLevelRequest != null ?
                Arrays.stream(DangerLevel.values())
                        .filter(d -> d.name().equalsIgnoreCase(dangerLevelRequest))
                        .findFirst()
                        .orElseThrow(() -> new InvalidEnumValueException("DangerLevel", "Invalid dangerLevel"))
                : null;

        MissionStatus missionStatus = missionStatusRequest != null ?
                Arrays.stream(MissionStatus.values())
                        .filter(m -> m.name().equalsIgnoreCase(missionStatusRequest))
                        .findFirst()
                        .orElseThrow(() -> new InvalidEnumValueException("MissionStatus", "Invalid missionStatus"))
                : null;

        PageRequest pageRequest = PageRequest.of(page,size, Sort.by("startedAt").ascending());
        Page<Mission> missions = missionRepository.findWithFilters(missionStatus,dangerLevel,startDateTime,endDateTime,pageRequest);

        return missions.map(m -> mapper.toMissionResponseDTO(m));
    }

    public MissionDetailsResponseDTO getMissionById(Long id) {
        Mission mission = missionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mission not found"));

        return mapper.toMissionDetailsResponseDTO(mission,adventurerMapper);
    }

    public Page<MissionMetricsResponseDTO> listMissionsMetrics(int size, int page) {
        PageRequest pageRequest = PageRequest.of(page,size, Sort.by("startedAt").ascending());

        Page<Mission> missions = missionRepository.findAll(pageRequest);
        return missions.map(mapper::toMissionMetricsResponseDTO);
    }
}
