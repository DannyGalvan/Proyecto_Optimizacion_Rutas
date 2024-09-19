package com.scaffolding.optimization.Services;

import com.scaffolding.optimization.api.AutoMapper.SupplierMapper;
import com.scaffolding.optimization.api.Controllers.CrudServiceProcessingController;
import com.scaffolding.optimization.database.Entities.Response.ResponseWrapper;
import com.scaffolding.optimization.database.Entities.models.Suppliers;
import com.scaffolding.optimization.database.dtos.SuppliersDTO;
import com.scaffolding.optimization.database.repositories.SuppliersRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class SupplierService extends CrudServiceProcessingController<Suppliers> {

    private final SuppliersRepository suppliersRepository;
    private final SupplierMapper supplierMapper;

    public SupplierService(SuppliersRepository suppliersRepository, SupplierMapper supplierMapper) {
        this.suppliersRepository = suppliersRepository;
        this.supplierMapper = supplierMapper;
    }

    @Override
    public ResponseWrapper executeCreation(Suppliers entity) {
        return new ResponseWrapper(true, "Supplier created successfully",
                Collections.singletonList(suppliersRepository.save(entity)));
    }

    @Override
    public ResponseWrapper executeUpdate(Suppliers entity) {
        return null;
    }

    @Override
    public ResponseWrapper executeDeleteById(Suppliers entity) {
        return null;
    }

    @Override
    public ResponseWrapper executeReadAll() {
        ResponseWrapper responseWrapper = new ResponseWrapper();
        responseWrapper.setSuccessful(true);
        responseWrapper.setMessage("suppliers found successfully");
        responseWrapper.setData(suppliersRepository.findAll());
        return responseWrapper;
    }

    public Suppliers mapDtoToEntity(SuppliersDTO supplier) {
        return supplierMapper.mapDtoToEntity(supplier);
    }

    public boolean existsByNames(String name) {
        return suppliersRepository.findByName(name);
    }
}
