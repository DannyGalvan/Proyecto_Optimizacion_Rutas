package com.scaffolding.optimization.database.repositories;

import com.scaffolding.optimization.database.Entities.models.Addresses;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressesRepository extends JpaRepository<Addresses, Long> {

    public Addresses findByCustomerId(Long customerId);
    public Addresses findByName(String name);

}