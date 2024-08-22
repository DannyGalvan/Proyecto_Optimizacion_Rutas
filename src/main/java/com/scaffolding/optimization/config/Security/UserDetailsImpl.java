package com.scaffolding.optimization.config.Security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.scaffolding.optimization.config.RoleConfig;
import com.scaffolding.optimization.database.Entities.models.Modules;
import com.scaffolding.optimization.database.Entities.models.Roles;
import com.scaffolding.optimization.database.Entities.models.RolesModules;
import com.scaffolding.optimization.database.Entities.models.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private final Users userEntity;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Roles authorities = userEntity.getRole();
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
        return userEntity.getEmail();
    }

    public long getId() {
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

    public String getAlias() {
        return userEntity.getAlias();
    }

    public List<Modules> getModules() {
        return userEntity.getRole().getRolesModules()
                .stream()
                .map(RolesModules::getModule)
                .collect(Collectors.toList());
    }

}