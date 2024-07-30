package com.scaffolding.optimization.Security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.scaffolding.optimization.Entities.Role;
import com.scaffolding.optimization.Entities.User;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private final User userEntity;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Role authorities = userEntity.getRole();
        List<String> roles = List.of(RoleConfig.ROLE_PREFIX + authorities.getName());

        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();

        roles.forEach(role -> {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role);
            simpleGrantedAuthorities.add(simpleGrantedAuthority);
        });

        return simpleGrantedAuthorities;
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getEmail();
    }

    public String getEmail() {
        return userEntity.getUserName();
    }

    public int getId() {
        return userEntity.getId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}