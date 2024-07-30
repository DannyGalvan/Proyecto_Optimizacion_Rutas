package com.scaffolding.optimization.Entities.Response;

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
    private int id;
    private String token;
    private String userName;
    private String email;
    private Collection<? extends GrantedAuthority> authorities;
    private String type = "Bearer";
    private String expirationDate;
}
