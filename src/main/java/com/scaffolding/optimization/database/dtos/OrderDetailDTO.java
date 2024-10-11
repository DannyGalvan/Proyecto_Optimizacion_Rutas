package com.scaffolding.optimization.database.dtos;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDetailDTO {

    @JsonIgnore
    @Min(value = 1, message = "Order ID is required")
    private Long orderId;

    @Min(value = 1, message = "Product ID is required")
    private Long productId;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal lineTotal;

    @Min(value = 1, message = "Quantity is required")
    private int quantity;

}
