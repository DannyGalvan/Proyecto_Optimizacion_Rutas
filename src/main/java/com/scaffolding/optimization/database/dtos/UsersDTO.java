package com.scaffolding.optimization.database.dtos;


import lombok.Data;

@Data
public class UsersDTO {
    private Long id;

    private String email;

    private String password;

    private String alias;

    private Long roleId;

    private Boolean deleted;

}
