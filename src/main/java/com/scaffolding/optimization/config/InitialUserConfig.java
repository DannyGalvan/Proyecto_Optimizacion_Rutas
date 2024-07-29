package com.scaffolding.optimization.config;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.scaffolding.optimization.Entities.Role;
import com.scaffolding.optimization.Entities.User;
import com.scaffolding.optimization.Repository.RoleRepository;
import com.scaffolding.optimization.Repository.UserRepository;

@Configuration
public class InitialUserConfig {
    @Bean
    public CommandLineRunner createInitialUser(UserRepository userRepository, RoleRepository roleRepository) {
        return args -> {
            String initialEmail = "admin@gmail.com";
            String initialPassword = "admin123";
            String initialUserName = "admin";
            String initialRole = "ADMIN";

            if (roleRepository.findByName(initialRole) == null) {
                Role role = new Role();
                role.setName(initialRole);
                role.setDescription("Administrator");
                role.setCreated_at(LocalDateTime.now());

                roleRepository.save(role); 
                
                if (userRepository.findByEmail(initialEmail) == null) {
                    User initialUser = new User();
                    initialUser.setEmail(initialEmail);
                    initialUser.setPassword(new BCryptPasswordEncoder().encode(initialPassword)); // Asumiendo que estás usando BCrypt para el cifrado de contraseñas
                    initialUser.setUserName(initialUserName);
                    initialUser.setName("Admin");
                    initialUser.setLastName("User");
                    initialUser.setActive(true);
                    initialUser.setCreated_at(LocalDateTime.now());
                    initialUser.setDateToken(null);
                    initialUser.setRole(role);
                    initialUser.setRole_id(role.getId());
    
                    userRepository.save(initialUser);
                }
            }            
        };
    }
}
