package com.scaffolding.optimization.database.dtos;


import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @Min(value = 1, message = "Customer ID is required")
    private Long customerId;

    @JsonIgnore
    private Timestamp orderDate = new Timestamp(System.currentTimeMillis());

    @JsonIgnore
    private Timestamp deliveryDate;

    @JsonIgnore
    private Long statusId;

    private BigDecimal total;

    private AddressesDTO address;

    private List<OrderDetailDTO> orderDetails;

    @JsonIgnore
    private Boolean deleted;

}
