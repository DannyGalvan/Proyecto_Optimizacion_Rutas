package com.scaffolding.optimization.database.repositories;

import com.scaffolding.optimization.database.Entities.models.Suppliers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuppliersRepository extends JpaRepository<Suppliers, Long>{

    boolean findByName(String name);

}