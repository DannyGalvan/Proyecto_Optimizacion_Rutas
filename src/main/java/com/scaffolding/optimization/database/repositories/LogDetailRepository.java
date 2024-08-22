package com.scaffolding.optimization.database.repositories;

import com.scaffolding.optimization.database.Entities.models.LogDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LogDetailRepository extends JpaRepository<LogDetail, Long>, JpaSpecificationExecutor<LogDetail> {

}