package com.scaffolding.optimization.database.repositories;

import com.scaffolding.optimization.database.Entities.models.LogHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LogHeaderRepository extends JpaRepository<LogHeader, Long>, JpaSpecificationExecutor<LogHeader> {

}