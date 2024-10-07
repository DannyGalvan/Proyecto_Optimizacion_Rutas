package com.scaffolding.optimization.database.repositories;

import com.scaffolding.optimization.database.Entities.models.Status;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StatusRepository extends JpaRepository<Status, Long>{

    public Status findByName(String name);

}