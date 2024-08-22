package com.scaffolding.optimization.database.repositories;

import com.scaffolding.optimization.database.Entities.models.Schedules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SchedulesRepository extends JpaRepository<Schedules, Long>, JpaSpecificationExecutor<Schedules> {

}