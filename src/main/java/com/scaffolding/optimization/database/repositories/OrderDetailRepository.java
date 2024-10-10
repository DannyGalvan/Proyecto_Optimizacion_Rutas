package com.scaffolding.optimization.database.repositories;

import com.scaffolding.optimization.database.Entities.models.OrderDetail;
import com.scaffolding.optimization.database.Entities.models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long>, JpaSpecificationExecutor<OrderDetail> {

    List<OrderDetail> findByOrder(Orders order);
}