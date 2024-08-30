package com.scaffolding.optimization.database.dtos;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.scaffolding.optimization.api.validator.UniqueValue;
import com.scaffolding.optimization.database.Entities.models.Roles;
import com.scaffolding.optimization.database.Entities.models.Users;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UsersDTO {
    private Long id;

    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email format")
    @NotEmpty(message = "Email cannot be empty")
    @UniqueValue(fieldName = "email", entityClass = Users.class, message = "Email already in use")
    private String email;

    @NotEmpty(message = "Password cannot be empty")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotEmpty(message = "Alias name cannot be empty")
    private String alias;

    private Roles role;

    private Boolean deleted = false;

}
