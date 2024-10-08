package com.scaffolding.optimization.database.repositories;

import com.scaffolding.optimization.database.Entities.models.Assignments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AssignmentsRepository extends JpaRepository<Assignments, Long>, JpaSpecificationExecutor<Assignments> {

}