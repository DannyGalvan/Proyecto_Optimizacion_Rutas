package com.scaffolding.optimization.Services;


import com.scaffolding.optimization.api.AutoMapper.VehicleMapper;
import com.scaffolding.optimization.api.Controllers.CrudServiceProcessingController;
import com.scaffolding.optimization.database.Entities.Response.ResponseWrapper;
import com.scaffolding.optimization.database.Entities.models.Vehicles;
import com.scaffolding.optimization.database.dtos.VehiclesDTO;
import com.scaffolding.optimization.database.repositories.VehiclesRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class VehiclesService extends CrudServiceProcessingController<Vehicles> {
    private final VehiclesRepository vehiclesRepository;
    private ResponseWrapper responseWrapper;

    private final VehicleMapper vehicleMapper;

    public VehiclesService(VehiclesRepository vehiclesRepository, VehicleMapper vehicleMapper) {
        this.vehiclesRepository = vehiclesRepository;
        this.vehicleMapper = vehicleMapper;
    }

    @Override
    public ResponseWrapper executeCreation(Vehicles entity) {
        vehiclesRepository.save(entity);
        return new ResponseWrapper(true, "Vehicle created successfully", Collections.singletonList(entity));
    }

    @Override
    public ResponseWrapper executeUpdate(Vehicles entity) {
        return null;
    }

    @Override
    public ResponseWrapper executeDeleteById(Vehicles entity) {
        return null;
    }

    @Override
    public ResponseWrapper executeReadAll() {
        responseWrapper = new ResponseWrapper();
        responseWrapper.setSuccessful(true);
        responseWrapper.setMessage("drivers found successfully");
        responseWrapper
                .setData(vehiclesRepository.findAll()
                        .stream().filter(vehicles -> !vehicles.getDeleted())
                        .map(vehicleMapper::mapEntityToDto).toList());

        return responseWrapper;
    }

    public Vehicles getVehicleById(Long id) {
        return vehiclesRepository.findById(id).orElse(null);
    }

    public Vehicles mapDtoToEntity(VehiclesDTO vehicle) {
        return vehicleMapper.mapDtoToEntity(vehicle);
    }

}
