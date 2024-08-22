package com.scaffolding.optimization.database.repositories;

import com.scaffolding.optimization.database.Entities.models.RolesModules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RolesModulesRepository extends JpaRepository<RolesModules, Long>, JpaSpecificationExecutor<RolesModules> {

}