package com.scaffolding.optimization.database.repositories;

import com.scaffolding.optimization.database.Entities.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsersRepository extends JpaRepository<Users, Long> {
    Users findByEmail(String email);

}