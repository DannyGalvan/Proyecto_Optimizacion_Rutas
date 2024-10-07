package com.scaffolding.optimization.Services;


import com.scaffolding.optimization.api.Controllers.CrudServiceProcessingController;
import com.scaffolding.optimization.database.Entities.Response.ResponseWrapper;
import com.scaffolding.optimization.database.Entities.models.Orders;
import com.scaffolding.optimization.database.repositories.OrdersRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class OrderService {

    private final OrdersRepository orderRepository;


    public OrderService(OrdersRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Orders executeCreation(Orders entity) {
        return orderRepository.save(entity);
    }


}
