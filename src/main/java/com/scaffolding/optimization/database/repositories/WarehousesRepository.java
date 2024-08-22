package com.scaffolding.optimization.database.repositories;

import com.scaffolding.optimization.database.Entities.models.Warehouses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface WarehousesRepository extends JpaRepository<Warehouses, Long>, JpaSpecificationExecutor<Warehouses> {

}