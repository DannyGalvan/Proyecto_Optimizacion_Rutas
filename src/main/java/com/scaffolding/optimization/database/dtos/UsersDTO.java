package com.scaffolding.optimization.database.dtos;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.scaffolding.optimization.database.Entities.models.Roles;
import lombok.Data;

@Data
public class UsersDTO {
    private Long id;

    private String email;

    @JsonIgnore
    private String password;

    private String alias;

    private Roles role;

    private Boolean deleted;

}
