package com.scaffolding.optimization.database.repositories;

import com.scaffolding.optimization.database.Entities.models.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<Products, Long>, ProductRepositoryCustom {
    boolean findByName(String name);

}