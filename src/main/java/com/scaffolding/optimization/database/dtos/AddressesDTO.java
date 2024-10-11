package com.scaffolding.optimization.database.dtos;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddressesDTO {
    private Long id;

    @NotEmpty(message = "Address name cannot be empty")
    private String name;

    @DecimalMin(value = "0.0", inclusive = false, message = "latitude must be greater than 0")
    private String longitude;

    @DecimalMin(value = "0.0", inclusive = false, message = "latitude must be greater than 0")
    private String latitude;

    @JsonIgnore
    private Boolean deleted;

    @JsonIgnore
    private Long idCustomer;

}
