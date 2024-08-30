package com.scaffolding.optimization.database.dtos;


import com.scaffolding.optimization.api.validator.UniqueValue;
import com.scaffolding.optimization.database.Entities.models.Customers;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;

@Data
public class CustomersDTO {
    private Long id;

    @NotEmpty(message = "first name cannot be empty")
    private String firstName;

    @NotEmpty(message = "Last name cannot be empty")
    private String lastName;

    @NotEmpty(message = "Phone cannot be empty")
    @Pattern(regexp = "^[0-9]{8}$", message = "Phone number must be 8 digits")
    @UniqueValue(fieldName = "phone", entityClass = Customers.class, message = "Phone number already in use")
    private String phone;

    @NotEmpty(message = "NIT cannot be empty")
    @Pattern(regexp = "^[0-9]{9}$", message = "NIT must be 14 digits")
    @UniqueValue(fieldName = "nit", entityClass = Customers.class, message = "NIT already in use")
    private String nit;

    @NotEmpty(message = "CUI cannot be empty")
    @Pattern(regexp = "^[0-9]{13}$", message = "CUI must be 14 digits")
    @UniqueValue(fieldName = "cui", entityClass = Customers.class, message = "CUI already in use")
    private String cui;

    @NotNull(message = "User cannot be null")
    @Valid
    private UsersDTO user;

    @NotNull(message = "Address cannot be null")
    @Valid
    private List<AddressesDTO> addresses;

}
