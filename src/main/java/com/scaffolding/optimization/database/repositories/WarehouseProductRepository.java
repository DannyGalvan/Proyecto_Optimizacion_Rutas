package com.scaffolding.optimization.database.repositories;

import com.scaffolding.optimization.database.Entities.models.WarehouseProduct;
import com.scaffolding.optimization.database.Entities.models.Warehouses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface WarehouseProductRepository extends JpaRepository<WarehouseProduct, Long>, JpaSpecificationExecutor<WarehouseProduct> {

    public List<WarehouseProduct> findByProductId(Long id);
    public List<WarehouseProduct> findByProductIdIn(List<Long> ids);
    public List<WarehouseProduct> findByWarehouse(Warehouses warehouse);
}