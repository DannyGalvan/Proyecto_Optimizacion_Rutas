package com.scaffolding.optimization.Entities.Response;

import java.time.LocalDateTime;

import com.scaffolding.optimization.Entities.Role;

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

    private Role role;
}
