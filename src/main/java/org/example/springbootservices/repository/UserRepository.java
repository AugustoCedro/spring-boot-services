package org.example.springbootservices.repository;

import org.example.springbootservices.model.audit.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
