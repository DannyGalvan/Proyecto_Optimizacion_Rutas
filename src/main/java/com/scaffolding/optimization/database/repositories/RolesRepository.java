package com.scaffolding.optimization.database.repositories;

import com.scaffolding.optimization.database.Entities.models.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RolesRepository extends JpaRepository<Roles, Long>, JpaSpecificationExecutor<Roles> {
    Roles findByName(String name);

}