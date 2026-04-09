package org.example.springbootservices.service;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.springbootservices.dto.AdventurerRequestDTO;
import org.example.springbootservices.dto.AdventurerUpdateDTO;
import org.example.springbootservices.dto.CompanionRequestDTO;
import org.example.springbootservices.dto.PagedResponseDTO;
import org.example.springbootservices.exception.InvalidEnumValueException;
import org.example.springbootservices.model.Adventurer;
import org.example.springbootservices.model.AdventurerClass;
import org.example.springbootservices.model.Specie;
import org.example.springbootservices.repository.AdventurerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class AdventurerService {

    private AdventurerRepository repository;

    public Adventurer registerAdventurer(AdventurerRequestDTO dto){

        AdventurerClass adventurerClass = Arrays.stream(AdventurerClass.values())
                                                .filter(c -> c.name().equalsIgnoreCase(dto.adventurerClass()))
                                                .findFirst()
                                                .orElseThrow(() -> new InvalidEnumValueException("AdventurerClass","Invalid adventurerClass"));

        Adventurer adventurer = new Adventurer(dto.name(),adventurerClass, dto.level());
        repository.add(adventurer);
        return adventurer;
    }

    public PagedResponseDTO<Adventurer> getAdventurersWithFilters(Boolean active, String adventurerClassRequest, Integer minLevel, int size, int page) {

        AdventurerClass adventurerClass = adventurerClassRequest != null ?
                Arrays.stream(AdventurerClass.values())
                .filter(c -> c.name().equalsIgnoreCase(adventurerClassRequest))
                .findFirst()
                .orElseThrow(() -> new InvalidEnumValueException("AdventurerClass","Invalid adventurerClass"))
        : null;

        List<Adventurer> filteredAdventurerList = repository.getWithFilters(active,adventurerClass,minLevel);
        int total = filteredAdventurerList.size();
        int fromIndex = page * size;
        int toIndex = Math.min(fromIndex + size,total);

        List<Adventurer> pageContent = fromIndex >= total ? List.of() : filteredAdventurerList.subList(fromIndex,toIndex);
        return new PagedResponseDTO<>(page,size,total,pageContent);
    }

    public Adventurer getAdventurerById(Long id){
        return repository.getById(id);
    }

    public void alterAdventurerData(Long id, @Valid AdventurerUpdateDTO dto) {
        AdventurerClass adventurerClass = dto.adventurerClass() != null ?
                Arrays.stream(AdventurerClass.values())
                        .filter(c -> c.name().equalsIgnoreCase(dto.adventurerClass()))
                        .findFirst()
                        .orElseThrow(() -> new InvalidEnumValueException("AdventurerClass","Invalid adventurerClass"))
                : null;
        repository.alterData(id,dto.name(),dto.level(),adventurerClass);
    }

    public void endTiesWithGuild(Long id) {
        repository.inactivateAdventurer(id);
    }

    public void recruitAdventurer(Long id) {
        repository.activateAdventurer(id);
    }

    public void setAdventurerCompanion(Long id, CompanionRequestDTO dto) {
        Specie specie = Arrays.stream(Specie.values())
                .filter(s -> s.name().equalsIgnoreCase(dto.specie()))
                .findFirst()
                .orElseThrow(() -> new InvalidEnumValueException("Specie","Invalid specie"));

        repository.setCompanion(id,dto.name(),dto.loyalty(),specie);
    }
}
