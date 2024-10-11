package com.scaffolding.optimization.database.Entities.Response.shipment;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderDetailWrapper {

    private String product;
    private int quantity;
    private BigDecimal price;
    private BigDecimal total;

    public OrderDetailWrapper(String product, int quantity, BigDecimal price, BigDecimal total) {
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.total = total;
    }
}
