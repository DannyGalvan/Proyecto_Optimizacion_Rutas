package com.scaffolding.optimization.Services;

import com.scaffolding.optimization.database.Entities.models.Roles;
import com.scaffolding.optimization.database.repositories.RolesRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RoleService{

    private final RolesRepository roleRepository;

    RoleService(RolesRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    public List<Roles> findAll(){
        return roleRepository.findAll();
    }

    public Roles findById(Long id){
        return roleRepository.findById(id).orElse(null);
    }

    public Roles findByName(String name){
        return roleRepository.findByName(name);
    }

}
