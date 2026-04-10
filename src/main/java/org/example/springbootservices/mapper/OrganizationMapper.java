package org.example.springbootservices.mapper;

import org.example.springbootservices.dto.OrganizationResponseDTO;
import org.example.springbootservices.model.audit.Organization;
import org.springframework.stereotype.Component;

@Component
public class OrganizationMapper {

    public OrganizationResponseDTO toOrganizationResponseDTO(Organization organization){
        return new OrganizationResponseDTO(
                organization.getId(),
                organization.getName(),
                organization.getActive()
        );
    }

}