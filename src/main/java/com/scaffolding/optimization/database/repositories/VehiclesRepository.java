package com.scaffolding.optimization.database.repositories;

import com.scaffolding.optimization.database.Entities.models.Vehicles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VehiclesRepository extends JpaRepository<Vehicles, Long>, JpaSpecificationExecutor<Vehicles> {

}