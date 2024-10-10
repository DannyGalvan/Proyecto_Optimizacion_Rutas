package com.scaffolding.optimization.database.repositories;

import com.scaffolding.optimization.database.Entities.models.Drivers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Time;
import java.util.List;

public interface DriversRepository extends JpaRepository<Drivers, Long>, JpaSpecificationExecutor<Drivers> {
    Drivers findByPhone(String phone);
    @Query("SELECT d FROM Drivers d WHERE d.schedule.startTime <= :currentTime AND d.schedule.endTime >= :currentTime")
    List<Drivers> findDriversByCurrentTime(@Param("currentTime") Time currentTime);

}