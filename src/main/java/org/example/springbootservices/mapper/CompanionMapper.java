package org.example.springbootservices.mapper;

import org.example.springbootservices.dto.CompanionResponseDTO;
import org.example.springbootservices.model.aventura.Companion;
import org.springframework.stereotype.Component;

@Component
public class CompanionMapper {

    public CompanionResponseDTO toCompanionResponseDTO(Companion companion){
        return new CompanionResponseDTO(
                companion.getId(),
                companion.getName(),
                companion.getSpecie(),
                companion.getLoyalty()
        );
    }


}
