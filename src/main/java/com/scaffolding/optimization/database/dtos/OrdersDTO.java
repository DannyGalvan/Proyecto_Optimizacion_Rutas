package com.scaffolding.optimization.database.dtos;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Data
public class OrdersDTO {

    private Long id;

    private Long assignmentId;

    @Min(value = 1, message = "Customer ID is required")
    private Long customerId;

    private Timestamp orderDate = Timestamp.valueOf(
            LocalDateTime.now(ZoneId.of("America/Guatemala"))
    );
    private Timestamp deliveryDate;
    private Long statusId;

    private BigDecimal total;

    private AddressesDTO address;

    private List<OrderDetailDTO> orderDetails;

    @JsonIgnore
    private Boolean deleted;

}
