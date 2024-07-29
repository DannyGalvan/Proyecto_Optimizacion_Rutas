package com.scaffolding.optimization.AutoMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.scaffolding.optimization.Entities.User;
import com.scaffolding.optimization.Entities.Response.UserResponse;


@Mapper(componentModel = "spring")
public interface UserMapper {

  UserResponse mapUserToUserResponse(User user);
  
  @Mapping(target = "password", ignore = true)
  User mapUserResponseToUser(UserResponse userResponse);
}
