package com.scaffolding.optimization.database.Entities.Response;

import java.time.LocalDateTime;

import com.scaffolding.optimization.database.Entities.models.Roles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Integer id;

    private Integer role_id;

    private String email;

    private String name;

    private String lastName;

    private String userName;

    private Boolean active = true;

    private LocalDateTime created_at;

    private LocalDateTime dateToken;

    private Roles role;
}
