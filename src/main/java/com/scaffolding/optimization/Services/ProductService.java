package com.scaffolding.optimization.Services;

import com.scaffolding.optimization.api.AutoMapper.ProductMapper;
import com.scaffolding.optimization.api.Controllers.CrudServiceProcessingController;
import com.scaffolding.optimization.database.Entities.Response.ResponseWrapper;
import com.scaffolding.optimization.database.Entities.models.Products;
import com.scaffolding.optimization.database.dtos.ProductsDTO;
import com.scaffolding.optimization.database.repositories.ProductsRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ProductService extends CrudServiceProcessingController<Products> {

    private final ProductMapper productMapper;
    private final ProductsRepository productsRepository;

    public ProductService(ProductMapper productMapper, ProductsRepository productsRepository) {
        this.productMapper = productMapper;
        this.productsRepository = productsRepository;
    }

    @Override
    public ResponseWrapper executeCreation(Products entity) {
        return  new ResponseWrapper(true, "Product created successfully",
                Collections.singletonList(productsRepository.save(entity)));
    }

    @Override
    public ResponseWrapper executeUpdate(Products entity) {
        return null;
    }

    @Override
    public ResponseWrapper executeDeleteById(Products entity) {
        return null;
    }

    @Override
    public ResponseWrapper executeReadAll() {
        ResponseWrapper responseWrapper = new ResponseWrapper();
        responseWrapper.setSuccessful(true);
        responseWrapper.setMessage("products found successfully");
        responseWrapper.setData(productsRepository.findAll());
        return responseWrapper;
    }

    public Products mapDtoToEntity(ProductsDTO product) {
        return productMapper.mapDtoToEntity(product);
    }

    public boolean existsByNames(String name) {
        return productsRepository.findByName(name);
    }

    public List<Products> executeDynamicQuery(String queryStr) {
        return productsRepository.executeDynamicQuery(queryStr);
    }
}
