package org.example.springbootservices.repository;

import org.example.springbootservices.model.aventura.Companion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanionRepository extends JpaRepository<Companion,Long> {
}
