package com.scaffolding.optimization.database.dtos;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddressesDTO {
    private Long id;

    private String name;

    private BigDecimal longitude;

    private BigDecimal latitude;

    private Boolean deleted;

    private Long idCustomer;

}
