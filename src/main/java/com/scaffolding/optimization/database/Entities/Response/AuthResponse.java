package com.scaffolding.optimization.database.Entities.Response;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private Long id;
    private String token;
    private String alias;
    private String email;
    private Collection<? extends GrantedAuthority> authorities;
    private String type = "Bearer";
    private Long role;
    private String expirationDate;
}
