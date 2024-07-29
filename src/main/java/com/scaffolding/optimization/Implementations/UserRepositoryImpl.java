package com.scaffolding.optimization.Implementations;

import org.springframework.stereotype.Service;

import com.scaffolding.optimization.Entities.User;
import com.scaffolding.optimization.Repository.UserRepositoryCustom;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Service
public class UserRepositoryImpl implements UserRepositoryCustom {

    private EntityManager entityManager;

    public UserRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public User findByRole(int role_id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> user = query.from(User.class);

        query.where(cb.equal(user.get("role_id"), role_id));

        return entityManager.createQuery(query).getSingleResult();
    }
    
}
