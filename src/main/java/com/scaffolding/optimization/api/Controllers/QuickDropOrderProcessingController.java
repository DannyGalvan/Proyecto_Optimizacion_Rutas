package com.scaffolding.optimization.api.Controllers;


import com.scaffolding.optimization.QuickDropConstants;
import com.scaffolding.optimization.QuickDropUtils;
import com.scaffolding.optimization.Services.CustomerService;
import com.scaffolding.optimization.Services.OrderService;
import com.scaffolding.optimization.database.Entities.Response.ResponseWrapper;
import com.scaffolding.optimization.database.Entities.models.*;
import com.scaffolding.optimization.database.dtos.OrderDetailDTO;
import com.scaffolding.optimization.database.dtos.OrdersDTO;
import com.scaffolding.optimization.database.repositories.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/orders")
public class QuickDropOrderProcessingController {


    private final OrderService orderService;
    private final AddressesRepository addressesRepository;

    private final ProductsRepository productsRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final StatusRepository statusRepository;
    private final CustomerService customerService;
    private final WarehouseProductRepository warehousesProductsRepository;

    public QuickDropOrderProcessingController(OrderService orderService, AddressesRepository addressesRepository, ProductsRepository productsRepository, OrderDetailRepository orderDetailRepository, StatusRepository statusRepository, CustomerService customerService, WarehouseProductRepository warehousesProductsRepository) {
        this.orderService = orderService;
        this.addressesRepository = addressesRepository;
        this.productsRepository = productsRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.statusRepository = statusRepository;
        this.customerService = customerService;
        this.warehousesProductsRepository = warehousesProductsRepository;
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseWrapper> createOrder(@RequestBody OrdersDTO order) {


        if (!checkStock(order.getOrderDetails())) {
            return ResponseEntity.badRequest().body(new ResponseWrapper(false, "Stock insuficiente para uno o más productos", Collections.singletonList("INSUFFICIENT_STOCK")));        }

        if (LocalTime.now().isAfter(QuickDropUtils.closeTime)) {
            order.setOrderDate(QuickDropUtils.getNextDay());
        }


        Orders newOrder = new Orders();
        List<OrderDetail> orderDetails = new ArrayList<>();

        Customers customer = customerService.findByUserID(order.getCustomerId());
        Optional<Addresses> customerAddress = Optional.ofNullable(addressesRepository.findByName(order.getAddress().getName()));
        Status status = statusRepository.findByName(QuickDropConstants.QuickDropStatus.CREATED.getStatus());

        if (customerAddress.isEmpty() || !customerAddress.get().getCustomer().getId().equals(customer.getId())) {
            Addresses addressSave = createNewAddress(order, customer);
            newOrder.setAddress(addressSave);
        } else {
            newOrder.setAddress(customerAddress.get());
        }

        setOrderDetails(newOrder, order, customer, status);


        Orders savedOrder = orderService.executeCreation(newOrder);
        saveOrderDetails(order, savedOrder, orderDetails);

        return ResponseEntity.ok(new ResponseWrapper(true, "Orden creada exitosamente", Collections.singletonList("CREATED")));    }


    @PutMapping("/{id}/{status}")
    public ResponseEntity<ResponseWrapper> getOrderById(@PathVariable Long id, @PathVariable String status) {
        Orders order = orderService.findById(id);
        Status newStatus = statusRepository.findByName(status);
        order.setStatus(newStatus);
        orderService.executeUpdate(order);
        return ResponseEntity.ok(new ResponseWrapper(true, "Order updated successfully", Collections.singletonList("UPDATED")));
    }

    private Addresses createNewAddress(OrdersDTO order, Customers customer) {
        Addresses address = new Addresses();
        address.setName(order.getAddress().getName());
        address.setLatitude(order.getAddress().getLatitude());
        address.setLongitude(order.getAddress().getLongitude());
        address.setCustomer(customer);
        return addressesRepository.save(address);
    }

    private void setOrderDetails(Orders newOrder, OrdersDTO order, Customers customer, Status status) {
        newOrder.setOrderDate(order.getOrderDate());
        newOrder.setCustomer(customer);
        newOrder.setTotal(order.getTotal());
        newOrder.setStatus(status);
    }

    private void saveOrderDetails(OrdersDTO order, Orders savedOrder, List<OrderDetail> orderDetails) {
        order.getOrderDetails().forEach(orderDetailDTO -> {
            Optional<Products> product = productsRepository.findById(orderDetailDTO.getProductId());
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setProduct(product.get());
            orderDetail.setQuantity(orderDetailDTO.getQuantity());
            orderDetail.setLineTotal(orderDetailDTO.getLineTotal());
            orderDetail.setOrder(savedOrder);
            orderDetails.add(orderDetail);
        });
        orderDetailRepository.saveAll(orderDetails);
    }

    private List<WarehouseProduct> getWarehouseProducts(List<Long> productIds) {
        return warehousesProductsRepository.findByProductIdIn(productIds);
    }

//    check if we have sufficient stock in the warehouse
    private boolean checkStock(List<OrderDetailDTO> orderDetails) {
        List<Long> productIds = new ArrayList<>();
        orderDetails.forEach(orderDetail -> productIds.add(orderDetail.getProductId()));
        List<WarehouseProduct> warehouseProducts = getWarehouseProducts(productIds);
        for (OrderDetailDTO orderDetail : orderDetails) {
            for (WarehouseProduct warehouseProduct : warehouseProducts) {
                if (orderDetail.getProductId().equals(warehouseProduct.getProduct().getId())) {
                    if (orderDetail.getQuantity() > warehouseProduct.getPartialStock()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

}
