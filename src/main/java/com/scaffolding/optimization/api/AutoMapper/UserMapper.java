package com.scaffolding.optimization.api.AutoMapper;

import com.scaffolding.optimization.database.Entities.models.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.scaffolding.optimization.database.Entities.Response.UserResponse;


@Mapper(componentModel = "spring")
public interface UserMapper {

  UserResponse mapUserToUserResponse(Users user);
  
  @Mapping(target = "password", ignore = true)
  Users mapUserResponseToUser(UserResponse userResponse);
}
