package com.scaffolding.optimization.Services;



import com.scaffolding.optimization.database.Entities.models.Orders;
import com.scaffolding.optimization.database.repositories.OrdersRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrdersRepository orderRepository;


    public OrderService(OrdersRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Orders executeCreation(Orders entity) {
        return orderRepository.save(entity);
    }
    public Orders executeUpdate(Orders entity) {
        return orderRepository.save(entity);
    }

    public Orders findById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }


}
