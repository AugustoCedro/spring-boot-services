package org.example.springbootservices.repository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import org.example.springbootservices.dto.AdventurerUpdateDTO;
import org.example.springbootservices.exception.ResourceNotFoundException;
import org.example.springbootservices.model.Adventurer;
import org.example.springbootservices.model.AdventurerClass;
import org.example.springbootservices.model.Companion;
import org.example.springbootservices.model.Specie;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AdventurerRepository {

    private final List<Adventurer> adventurerList = new ArrayList<>();

    public AdventurerRepository() {
        for (int i = 1; i <= 100; i++) {
            AdventurerClass[] adventurerClasses = AdventurerClass.values();
            AdventurerClass adventurerClass = adventurerClasses[i % adventurerClasses.length];

            adventurerList.add(new Adventurer("Aventureiro" + i, adventurerClass, (i % 20) + 1));
        }
    }

    public void add(Adventurer adventurer){
        adventurerList.add(adventurer);
    }

    public List<Adventurer> getWithFilters(Boolean active, AdventurerClass adventurerClass, Integer minLevel){
        return adventurerList.stream()
                .filter(a ->
                        (active == null || a.getActive().equals(active)) &&
                        (adventurerClass == null || a.getAdventurerClass().equals(adventurerClass)) &&
                        (minLevel == null || a.getLevel() >= minLevel)

                ).toList();
    }

    public Adventurer getById(Long id) {
        return adventurerList.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst().orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
    }

    public void alterData(Long id, String name,Integer level, AdventurerClass adventurerClass) {
        Adventurer adventurer = getById(id);

        Optional.ofNullable(name).ifPresent(adventurer::setName);
        Optional.ofNullable(level).ifPresent(adventurer::setLevel);
        Optional.ofNullable(adventurerClass).ifPresent(adventurer::setAdventurerClass);

    }

    public void inactivateAdventurer(Long id) {
        Adventurer adventurer = getById(id);
        adventurer.setActive(false);
    }

    public void activateAdventurer(Long id) {
        Adventurer adventurer = getById(id);
        adventurer.setActive(true);
    }

    public void setCompanion(Long id, String name, Integer loyalty, Specie specie) {
        Adventurer adventurer = getById(id);
        Companion companion = new Companion(name,specie,loyalty);
        adventurer.setCompanion(companion);
    }
}
