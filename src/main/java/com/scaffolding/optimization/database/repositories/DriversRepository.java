package com.scaffolding.optimization.database.repositories;

import com.scaffolding.optimization.database.Entities.models.Drivers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DriversRepository extends JpaRepository<Drivers, Long>, JpaSpecificationExecutor<Drivers> {
    Drivers findByPhone(String phone);

}