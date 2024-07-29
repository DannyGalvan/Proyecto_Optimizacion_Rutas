package com.scaffolding.optimization.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scaffolding.optimization.Entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, UserRepositoryCustom {
  List<User> findAll();

  User findByUserName(String username);

  User findByEmail(String email);

  User findByUserNameAndPassword(String username, String password);

  User findByEmailAndPassword(String email, String password);

  User findByUserNameOrEmail(String username, String email);
}
