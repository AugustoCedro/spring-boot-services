package org.example.springbootservices.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.example.springbootservices.dto.AdventurerRequestDTO;
import org.example.springbootservices.dto.AdventurerUpdateDTO;
import org.example.springbootservices.dto.CompanionRequestDTO;
import org.example.springbootservices.dto.PagedResponseDTO;
import org.example.springbootservices.model.Adventurer;
import org.example.springbootservices.model.Companion;
import org.example.springbootservices.service.AdventurerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aventureiros")
@AllArgsConstructor
@Validated
public class AdventurerController {

    private AdventurerService service;

    @PostMapping
    public ResponseEntity<Adventurer> registerAdventurer(@RequestBody @Valid AdventurerRequestDTO dto){
        Adventurer adventurer = service.registerAdventurer(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(adventurer);
    }

    @GetMapping
    public ResponseEntity<PagedResponseDTO<Adventurer>> listAdventurersWithFilters(
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
        PagedResponseDTO<Adventurer> paged = service.getAdventurersWithFilters(active,adventurerClass,minLevel,size,page);
        return ResponseEntity.ok()
                .header("X-Total-Count",String.valueOf(paged.total()))
                .header("X-Page",String.valueOf(paged.page()))
                .header("X-Size",String.valueOf(paged.size()))
                .header("X-Total-Pages",String.valueOf(paged.totalPages()))
                .body(paged);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Adventurer> getAdventurerById(@PathVariable Long id){
        Adventurer adventurer = service.getAdventurerById(id);
        return ResponseEntity.ok().body(adventurer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> alterAdventurerData(@PathVariable Long id, @RequestBody @Valid AdventurerUpdateDTO dto){
        service.alterAdventurerData(id,dto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/guilda/encerrar")
    public ResponseEntity<Adventurer> endTiesWithGuild(@PathVariable Long id){
        service.endTiesWithGuild(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/guilda/recrutar")
    public ResponseEntity<Adventurer> recruitAdventurer(@PathVariable Long id){
        service.recruitAdventurer(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/companheiro")
    public ResponseEntity<Adventurer> setAdventurerCompanion(@PathVariable Long id, @RequestBody @Valid CompanionRequestDTO dto){
        service.setAdventurerCompanion(id,dto);
        return ResponseEntity.noContent().build();
    }


}
