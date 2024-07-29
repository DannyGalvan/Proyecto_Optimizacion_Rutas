package com.scaffolding.optimization.Security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.scaffolding.optimization.Entities.User;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private final User userEntity;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<String> authorities = List.of("admin", "medical");
        List<String> roles = List.of(RoleConfig.ROLE_PREFIX + authorities.get(0), RoleConfig.ROLE_PREFIX + authorities.get(1));

        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        
        authorities.forEach(authority -> {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
            simpleGrantedAuthorities.add(simpleGrantedAuthority);
        });

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
        return userEntity.getEmail();
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