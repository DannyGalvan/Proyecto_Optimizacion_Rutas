package com.scaffolding.optimization.database.dtos;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDetailDTO {
    private Long orderId;

    private Long productId;

    private BigDecimal lineTotal;

    private Long quantity;

}
