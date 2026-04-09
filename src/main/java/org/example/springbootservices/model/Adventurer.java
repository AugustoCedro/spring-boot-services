package org.example.springbootservices.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Adventurer {

    private Long id;
    private String name;
    private AdventurerClass adventurerClass;
    private Integer level;
    private Boolean active;
    private Companion companion;
    private static Long lastId = 1L;

    public Adventurer(String name, AdventurerClass adventurerClass, Integer level) {
        this.id = lastId++;
        this.name = name;
        this.adventurerClass = adventurerClass;
        this.level = level;
        this.active = true;
    }
}
