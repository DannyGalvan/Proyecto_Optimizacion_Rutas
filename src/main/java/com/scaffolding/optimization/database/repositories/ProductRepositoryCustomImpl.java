package com.scaffolding.optimization.database.repositories;

import com.scaffolding.optimization.database.Entities.models.Products;


import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    private final EntityManager entityManager;

    public ProductRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List executeDynamicQuery(String queryStr) {
        Query query = entityManager.createQuery(queryStr, Products.class);
        return query.getResultList();
    }
}
