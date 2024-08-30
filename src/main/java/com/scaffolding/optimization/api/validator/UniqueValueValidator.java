package com.scaffolding.optimization.api.validator;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueValueValidator  implements ConstraintValidator<UniqueValue, Object> {

    private String fieldName;
    private Class<?> entityClass;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void initialize(UniqueValue constraintAnnotation) {
        this.fieldName = constraintAnnotation.fieldName();
        this.entityClass = constraintAnnotation.entityClass();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        if (value == null) {
            return true;
        }

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<?> root = criteriaQuery.from(entityClass);

        Predicate predicate = criteriaBuilder.equal(root.get(fieldName), value);
        criteriaQuery.select(criteriaBuilder.count(root)).where(predicate);

        Long resultCount = entityManager.createQuery(criteriaQuery).getSingleResult();

        return resultCount == 0;
    }
}
