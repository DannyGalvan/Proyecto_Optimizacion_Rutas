package com.scaffolding.optimization.database.dtos;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Data
public class OrdersDTO {
    private Long id;


    private Long assignmentId;

    @Min(value = 1, message = "Customer ID is required")
    private Long customerId;

    private Timestamp orderDate = new Timestamp(System.currentTimeMillis());

    private Date deliveryDate;

    private Long statusId;

    private BigDecimal total;

    private AddressesDTO address;

    private List<OrderDetailDTO> orderDetails;

    private Boolean deleted;

}
