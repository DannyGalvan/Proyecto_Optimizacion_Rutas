package com.scaffolding.optimization.Services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


import com.scaffolding.optimization.api.Controllers.CrudServiceProcessingController;
import com.scaffolding.optimization.database.Entities.Response.ResponseWrapper;
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
public class UserService extends CrudServiceProcessingController<Users> implements UserDetailsService, IUserService  {
    
    private final UsersRepository userRepository;
    private final UserMapper userMapper;
    private ResponseWrapper responseWrapper;
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

    @Override
    public ResponseWrapper executeCreation(Users entity) {
        responseWrapper = new ResponseWrapper();
        responseWrapper.setData(Collections.singletonList(userRepository.save(entity)));
        return responseWrapper;
    }

    @Override
    public ResponseWrapper executeUpdate(Users entity) {
        responseWrapper = new ResponseWrapper();
        Optional<Users> userEntityFound = userRepository.findById(entity.getId());
        userEntityFound.ifPresent(userEntity -> userEntity.setEmail(entity.getEmail()));
        responseWrapper.setSuccessful(true);
        responseWrapper.setMessage("usuario actualizado exitosamente");
        return responseWrapper;
    }

    @Override
    public ResponseWrapper executeDeleteById(Users entity) {
        Optional<Users> userEntityFound = userRepository.findById(entity.getId());

        if (userEntityFound.isPresent()) {
            userEntityFound.get().setDeleted(true);
            responseWrapper.setSuccessful(true);
            responseWrapper.setMessage("usuario eliminado exitosamente");
            return responseWrapper;
        }

        responseWrapper.setSuccessful(false);
        responseWrapper.setMessage("usuario no encontrado");
        return responseWrapper;
    }

    @Override
    public ResponseWrapper executeReadAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
