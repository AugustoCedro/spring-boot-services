package org.example.springbootservices.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.example.springbootservices.dto.MissionDetailsResponseDTO;
import org.example.springbootservices.dto.MissionMetricsResponseDTO;
import org.example.springbootservices.dto.MissionRequestDTO;
import org.example.springbootservices.dto.MissionResponseDTO;
import org.example.springbootservices.model.aventura.Participation;
import org.example.springbootservices.service.MissionService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/missoes")
@AllArgsConstructor
@Validated
public class MissionController {

    private MissionService service;

    @PostMapping
    public ResponseEntity<MissionDetailsResponseDTO> registerMission(@RequestBody @Valid MissionRequestDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registerMission(dto));
    }

    @PostMapping("/{missionId}/participantes/{adventurerId}")
    public ResponseEntity<MissionDetailsResponseDTO> registerParticipant(@PathVariable Long missionId, @PathVariable Long adventurerId){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registerParticipant(missionId,adventurerId));
    }

    @GetMapping
    public Page<MissionResponseDTO> listMissionsWithFilters(
            @RequestHeader(value = "X-Page",required = false,defaultValue = "0")
            @Min(value = 0, message = "Page cannot be negative.")
            int page,
            @RequestHeader(value = "X-Size",required = false, defaultValue = "10")
            @Min(value = 1, message = "Size must be at least 1.")
            @Max(value = 50, message = "Size should be a maximum of 50.")
            int size,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String dangerLevel,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate
    ){
        return service.listMissionsWithFilters(status,dangerLevel,startDate,endDate,size,page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MissionDetailsResponseDTO> getMissionById(@PathVariable Long id){
        return ResponseEntity.ok().body(service.getMissionById(id));
    }

    @GetMapping("/metricas")
    public Page<MissionMetricsResponseDTO> listMissionMetrics(
            @RequestHeader(value = "X-Page",required = false,defaultValue = "0")
            @Min(value = 0, message = "Page cannot be negative.")
            int page,
            @RequestHeader(value = "X-Size",required = false, defaultValue = "10")
            @Min(value = 1, message = "Size must be at least 1.")
            @Max(value = 50, message = "Size should be a maximum of 50.")
            int size
    ){
        return service.listMissionsMetrics(size,page);
    }

}
