package com.scaffolding.optimization.database.repositories;

import com.scaffolding.optimization.database.Entities.models.Customers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CustomersRepository extends JpaRepository<Customers, Long>, JpaSpecificationExecutor<Customers> {
    Customers findByNit(String nit);
    Customers findByPhone(String phone);
    Customers findByCui(String cui);
    Customers findByUserId(Long userId);

}