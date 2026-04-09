package org.example.springbootservices.repository;

import org.example.springbootservices.model.audit.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization,Long> {
}
