package com.scaffolding.optimization.database.Entities.Response.shipment;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;


@Setter
@Getter
@NoArgsConstructor
public class ShipmentResponseWrapper {
    private String message;
    private BigDecimal totalWarehousePickupCost;
    private BigDecimal deliveryTransportationCost;
    private CustomerWrapper customer;
    private OrderWrapper order;
    private VehicleWrapper vehicle;
    List<WarehouseWrapper> warehouses;

    public ShipmentResponseWrapper(String message) {
        this.message = message;
    }
}
