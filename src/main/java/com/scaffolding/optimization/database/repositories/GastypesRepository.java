package com.scaffolding.optimization.database.repositories;

import com.scaffolding.optimization.database.Entities.models.Gastypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GastypesRepository extends JpaRepository<Gastypes, Long>, JpaSpecificationExecutor<Gastypes> {

}