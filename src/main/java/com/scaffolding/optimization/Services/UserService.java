package com.scaffolding.optimization.Services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.scaffolding.optimization.QuickDropUtils;
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

    @Override
    protected ResponseWrapper validateForCreation(Users entity) {
        responseWrapper = new ResponseWrapper();

        if (!QuickDropUtils.verifyEmailFormat(entity.getEmail())) {
            responseWrapper.addError("email","el formato del correo es invalido");
        }

        if (entity.getEmail() == null) {
            responseWrapper.addError("email","email es requerido");
        }

        if (entity.getPassword() == null) {
            responseWrapper.addError("password","password es requerido");
        }

        if (entity.getRole() == null) {
            responseWrapper.addError("role","role es requerido");
        }

        Users  userEntityFound = userRepository.findByEmail(entity.getEmail());
        if (userEntityFound != null) {
            responseWrapper.addError("email","el correo ya existe");
        }

        return responseWrapper;
    }

    @Override
    protected ResponseWrapper validateForUpdate(Users entity) {
        responseWrapper = new ResponseWrapper();
        if (entity.getEmail() == null) {
            responseWrapper.addError("email","email es requerido");
        }

        if (!QuickDropUtils.verifyEmailFormat(entity.getEmail())) {
            responseWrapper.addError("email","formato del email no es valido");
        }

        Users  userEntityFound = userRepository.findByEmail(entity.getEmail());
        if (userEntityFound != null) {
            if (!userEntityFound.getId().equals(entity.getId())) {
                responseWrapper.addError("email","el correo ya existe");
            }
        }
        return responseWrapper;
    }

    @Override
    protected ResponseWrapper validateForDelete(Users entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected ResponseWrapper validateForRead(Users entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
