package com.scaffolding.optimization.database.dtos;


import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

@Data
public class OrdersDTO {
    private Long id;

    private Long assignmentId;

    private Long customerId;

    private Date orderDate;

    private Date deliveryDate;

    private Long statusId;

    private BigDecimal total;

    private Boolean deleted;

}
