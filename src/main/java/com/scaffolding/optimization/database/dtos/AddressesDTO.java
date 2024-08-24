package com.scaffolding.optimization.database.dtos;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddressesDTO {
    private Long id;

    private String name;

    private BigDecimal longitude;

    private BigDecimal latitude;

    private Boolean deleted;

    @JsonIgnore
    private Long idCustomer;

}
