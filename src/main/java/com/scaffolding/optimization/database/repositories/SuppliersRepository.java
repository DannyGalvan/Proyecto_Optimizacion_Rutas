package com.scaffolding.optimization.database.repositories;

import com.scaffolding.optimization.database.Entities.models.Suppliers;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SuppliersRepository extends JpaRepository<Suppliers, Long>{

    Optional<Suppliers> findByName(String name);
}