package com.scaffolding.optimization.database.repositories;

import com.scaffolding.optimization.database.Entities.models.Addresses;
import com.scaffolding.optimization.database.Entities.models.Customers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressesRepository extends JpaRepository<Addresses, Long> {

    public Addresses findByCustomerId(Long customerId);
    public Addresses findByName(String name);
    public List<Addresses> findByCustomer(Customers customer);

}