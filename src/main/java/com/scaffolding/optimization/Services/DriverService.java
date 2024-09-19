package com.scaffolding.optimization.Services;

import com.scaffolding.optimization.api.AutoMapper.DriverMapper;
import com.scaffolding.optimization.api.Controllers.CrudServiceProcessingController;
import com.scaffolding.optimization.database.Entities.Response.ResponseWrapper;
import com.scaffolding.optimization.database.Entities.models.Drivers;
import com.scaffolding.optimization.database.dtos.DriversDTO;
import com.scaffolding.optimization.database.repositories.DriversRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DriverService extends CrudServiceProcessingController<Drivers> {

    private final DriversRepository driverRepository;
    private ResponseWrapper responseWrapper;

    private final DriverMapper driverMapper;

    public DriverService(DriversRepository driverRepository, DriverMapper driverMapper) {
        this.driverRepository = driverRepository;
        this.driverMapper = driverMapper;
    }

    @Override
    public ResponseWrapper executeCreation(Drivers entity) {
        responseWrapper = new ResponseWrapper();
        driverRepository.save(entity);
        responseWrapper.setSuccessful(true);
        responseWrapper.setMessage("conductor creado exitosamente");
        return responseWrapper;
    }

    @Override
    public ResponseWrapper executeUpdate(Drivers entity) {
        responseWrapper = new ResponseWrapper();
        Optional<Drivers> driversEntityFound = driverRepository.findById(entity.getId());
        if (driversEntityFound.isPresent()) {
            driversEntityFound.get().setName(entity.getName() != null ? entity.getName() : driversEntityFound.get().getName());
            driversEntityFound.get().setPhone(entity.getPhone() != null ? entity.getPhone() : driversEntityFound.get().getPhone());
            driversEntityFound.get().setSchedule(entity.getSchedule() != null ? entity.getSchedule() : driversEntityFound.get().getSchedule());
            driverRepository.save(driversEntityFound.get());
            responseWrapper.setSuccessful(true);
            responseWrapper.setMessage("conductor actualizado exitosamente");
            return responseWrapper;
        }
        responseWrapper.setSuccessful(false);
        responseWrapper.addError("conductor","cliente no encontrado");
        return responseWrapper;
    }

    @Override
    public ResponseWrapper executeDeleteById(Drivers entity) {
        responseWrapper = new ResponseWrapper();
        Optional<Drivers> driversEntityFound = driverRepository.findById(entity.getId());
        if (driversEntityFound.isPresent()) {
            driversEntityFound.get().getUser().setDeleted(true);
            driverRepository.save(driversEntityFound.get());
            responseWrapper.setSuccessful(true);
            responseWrapper.setMessage("empleado eliminado exitosamente");
            return responseWrapper;
        }
        responseWrapper.setSuccessful(false);
        responseWrapper.addError("conductor","empleado no encontrado");
        return responseWrapper;
    }

    @Override
    public ResponseWrapper executeReadAll() {
        responseWrapper = new ResponseWrapper();
        responseWrapper.setSuccessful(true);
        responseWrapper.setMessage("drivers found successfully");
        responseWrapper
                .setData(driverRepository.findAll()
                        .stream().filter(drivers -> !drivers.getUser().getDeleted())
                        .map(driverMapper::mapEntityToDto).toList());

        return responseWrapper;
    }

    public Drivers findDriverById(Long id) {
        return driverRepository.findById(id).orElse(null);
    }

    public Drivers mapToEntityDriver(DriversDTO driverDTO) {
        return driverMapper.mapDtoToEntity(driverDTO);
    }
}
