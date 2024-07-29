package com.scaffolding.optimization.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scaffolding.optimization.Entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>  {
    Role findByName(String name);
}
