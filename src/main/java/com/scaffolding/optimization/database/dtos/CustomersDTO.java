package com.scaffolding.optimization.database.dtos;


import lombok.Data;

import java.util.List;

@Data
public class CustomersDTO {
    private Long id;

    private String firstName;

    private String lastName;

    private String phone;

    private UsersDTO user;

    private String nit;

    private String cui;

    private List<AddressesDTO> addresses;

}
