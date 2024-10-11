package com.scaffolding.optimization.database.Entities.Response.shipment;

import com.scaffolding.optimization.database.Entities.models.Customers;
import lombok.Getter;
import lombok.Setter;
import org.checkerframework.checker.index.qual.SearchIndexBottom;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;


@Getter
@Setter
public class OrderWrapper {
    private Long id;
    private Timestamp orderDate;
    private BigDecimal total;
    private List<OrderDetailWrapper> orderDetail;

    public OrderWrapper(Long id, Timestamp orderDate, BigDecimal total, List<OrderDetailWrapper> orderDetail) {
        this.id = id;
        this.orderDate = orderDate;
        this.total = total;
        this.orderDetail = orderDetail;
    }
}
