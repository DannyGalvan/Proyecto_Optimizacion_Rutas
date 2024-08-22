package com.scaffolding.optimization.database.repositories;

import com.scaffolding.optimization.database.Entities.models.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StatusRepository extends JpaRepository<Status, Long>, JpaSpecificationExecutor<Status> {

}