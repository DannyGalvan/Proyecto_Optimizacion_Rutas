package com.scaffolding.optimization.Services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.scaffolding.optimization.AutoMapper.UserMapper;
import com.scaffolding.optimization.Entities.User;
import com.scaffolding.optimization.Entities.Response.UserResponse;
import com.scaffolding.optimization.Interfaces.IUserService;
import com.scaffolding.optimization.Repository.UserRepository;
import com.scaffolding.optimization.Security.UserDetailsImpl;;

@Service
public class UserService implements UserDetailsService, IUserService {
    
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User userEntity = userRepository.findByEmail(email);

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
