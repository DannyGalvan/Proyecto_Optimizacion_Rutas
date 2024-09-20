package com.scaffolding.optimization.database.repositories;

import com.scaffolding.optimization.database.Entities.models.Products;


import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    private final EntityManager entityManager;

    public ProductRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

     @Override
    public List<Products> executeDynamicQuery(String search, Integer classificationId, Integer supplierId) {
        // Crear el CriteriaBuilder
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        
        // Crear la consulta
        CriteriaQuery<Products> criteriaQuery = criteriaBuilder.createQuery(Products.class);
        
        // Definir el root de la consulta (la tabla Products)
        Root<Products> root = criteriaQuery.from(Products.class);
        
        // Lista de predicados para los filtros
        List<Predicate> predicates = new ArrayList<>();
        
        // Filtro de búsqueda por nombre o descripción
        if (search != null && !search.isEmpty()) {
            Predicate searchPredicate = criteriaBuilder.like(root.get("name"), "%" + search + "%");
            predicates.add(searchPredicate);
        }
        
        // Filtro por clasificación
        if (classificationId != null) {
            Predicate classificationPredicate = criteriaBuilder.equal(root.get("classification").get("id"), classificationId);
            predicates.add(classificationPredicate);
        }

        // Filtro por proveedor
        if (supplierId != null) {
            Predicate supplierPredicate = criteriaBuilder.equal(root.get("supplier").get("id"), supplierId);
            predicates.add(supplierPredicate);
        }
        
        // Aplicar los predicados a la consulta
        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        
        // Crear y ejecutar la consulta
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
