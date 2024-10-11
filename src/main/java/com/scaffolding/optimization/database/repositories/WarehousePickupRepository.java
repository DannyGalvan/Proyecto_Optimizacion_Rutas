package com.scaffolding.optimization.database.repositories;

import com.scaffolding.optimization.database.Entities.models.WarehousePickup;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WarehousePickupRepository extends JpaRepository<WarehousePickup, Long> {
    public List<WarehousePickup> findByAssignmentId(Long id);
}
