package com.scaffolding.optimization.database.repositories;

import com.scaffolding.optimization.database.Entities.models.Products;

import java.util.List;

public interface ProductRepositoryCustom {
    List<Products> executeDynamicQuery(String queryStr);
}
