package com.scaffolding.optimization.Interfaces;

import java.util.List;

import com.scaffolding.optimization.database.Entities.Response.UserResponse;

public interface IUserService {
    List<UserResponse> findAll();
}
