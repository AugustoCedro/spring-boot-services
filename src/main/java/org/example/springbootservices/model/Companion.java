package org.example.springbootservices.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
@AllArgsConstructor
public class Companion {

    private String name;
    private Specie specie;
    private Integer loyalty;
}
