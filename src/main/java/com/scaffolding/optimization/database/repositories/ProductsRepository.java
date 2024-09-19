package com.scaffolding.optimization.database.repositories;

import com.scaffolding.optimization.database.Entities.models.Products;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<Products, Long>, ProductRepositoryCustom {
    Optional<Products> findByName(String name);

}