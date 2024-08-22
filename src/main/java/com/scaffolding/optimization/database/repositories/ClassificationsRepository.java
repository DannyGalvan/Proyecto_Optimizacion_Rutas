package com.scaffolding.optimization.database.repositories;

import com.scaffolding.optimization.database.Entities.models.Classifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ClassificationsRepository extends JpaRepository<Classifications, Long>, JpaSpecificationExecutor<Classifications> {

}