package org.example.springbootservices.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.example.springbootservices.dto.*;
import org.example.springbootservices.service.AdventurerService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/aventureiros")
@AllArgsConstructor
@Validated
public class AdventurerController {

    private AdventurerService service;

    @PostMapping
    public ResponseEntity<AdventurerSearchResponseDTO> registerAdventurer(@RequestBody @Valid AdventurerRequestDTO dto){

        return ResponseEntity.status(HttpStatus.CREATED).body(service.register(dto));
    }

    @PostMapping("/{adventurerId}/companheiros")
    public ResponseEntity<AdventurerSearchResponseDTO> registerAdventurerCompanion(@PathVariable Long adventurerId,@RequestBody @Valid CompanionRequestDTO dto){
        AdventurerSearchResponseDTO adventurerDTO = service.registerCompanion(adventurerId,dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(adventurerDTO);
    }

    @GetMapping
    public Page<AdventurerResponseDTO> listAdventurersWithFilters(
            @RequestHeader(value = "X-Page",required = false,defaultValue = "0")
            @Min(value = 0, message = "Page cannot be negative.")
            int page,
            @RequestHeader(value = "X-Size",required = false, defaultValue = "10")
            @Min(value = 1, message = "Size must be at least 1.")
            @Max(value = 50, message = "Size should be a maximum of 50.")
            int size,
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false) String adventurerClass,
            @RequestParam(required = false) Integer minLevel
    ){
        return service.listAdventurersWithFilters(active,adventurerClass,minLevel,size,page);
    }

    @GetMapping("/buscar")
    public Page<AdventurerSearchResponseDTO> listingAdventurersByName(
            @RequestHeader(value = "X-Page",required = false,defaultValue = "0")
            @Min(value = 0, message = "Page cannot be negative.")
            int page,
            @RequestHeader(value = "X-Size",required = false, defaultValue = "10")
            @Min(value = 1, message = "Size must be at least 1.")
            @Max(value = 50, message = "Size should be a maximum of 50.")
            int size,
            @RequestParam String name
    ){
        return service.listAdventurersByName(name,size,page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdventurerDetailsResponseDTO> getAdventurerById(@PathVariable Long id){
        return ResponseEntity.ok().body(service.getAdventurerById(id));
    }

    @GetMapping("/ranking")
    public Page<AdventurerDetailsResponseDTO> listingAdventurersRanking(
            @RequestHeader(value = "X-Page",required = false,defaultValue = "0")
            @Min(value = 0, message = "Page cannot be negative.")
            int page,
            @RequestHeader(value = "X-Size",required = false, defaultValue = "10")
            @Min(value = 1, message = "Size must be at least 1.")
            @Max(value = 50, message = "Size should be a maximum of 50.")
            int size,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate
    ){
        return service.listAdventurersRanking(status,startDate,endDate,size,page);
    }




}
