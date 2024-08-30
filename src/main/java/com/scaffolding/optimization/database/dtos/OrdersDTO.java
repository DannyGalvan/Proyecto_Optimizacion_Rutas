package com.scaffolding.optimization.database.dtos;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

@Data
public class OrdersDTO {
    private Long id;

    @Min(value = 1, message = "Assignment ID is required")
    private Long assignmentId;

    @Min(value = 1, message = "Customer ID is required")
    private Long customerId;

    private Date orderDate = new Date(System.currentTimeMillis());

    private Date deliveryDate;

    @Min(value = 1, message = "Status ID is required")
    private Long statusId;

    @DecimalMin(value = "0.0", inclusive = false, message = "Total must be greater than 0")
    private BigDecimal total;

    private Boolean deleted;

}
