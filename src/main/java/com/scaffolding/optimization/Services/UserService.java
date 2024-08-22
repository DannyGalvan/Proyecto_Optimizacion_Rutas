package com.scaffolding.optimization.Services;

import java.util.List;

import com.scaffolding.optimization.database.Entities.models.Users;
import com.scaffolding.optimization.database.repositories.UsersRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.scaffolding.optimization.api.AutoMapper.UserMapper;
import com.scaffolding.optimization.database.Entities.Response.UserResponse;
import com.scaffolding.optimization.Interfaces.IUserService;
import com.scaffolding.optimization.config.Security.UserDetailsImpl;;

@Service
public class UserService implements UserDetailsService, IUserService {
    
    private final UsersRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UsersRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users userEntity = userRepository.findByEmail(email);

        if (userEntity == null) {
            throw new UsernameNotFoundException(email);
        }

        return new UserDetailsImpl(userEntity);
    }

    @Override
    public List<UserResponse> findAll() {
        return userRepository.findAll().stream().map(userMapper::mapUserToUserResponse).toList();
    }
}
