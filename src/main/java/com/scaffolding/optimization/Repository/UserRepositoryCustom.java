package com.scaffolding.optimization.Repository;

import com.scaffolding.optimization.Entities.User;

public interface UserRepositoryCustom {
    User findByRole(int role_id);
}
