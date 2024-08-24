package com.scaffolding.optimization.Services;

import com.scaffolding.optimization.api.AutoMapper.GasTypeMapper;
import com.scaffolding.optimization.database.Entities.Response.ResponseWrapper;
import com.scaffolding.optimization.database.Entities.models.Gastypes;
import com.scaffolding.optimization.database.dtos.GastypesDTO;
import com.scaffolding.optimization.database.repositories.GastypesRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class GasTypeService {

    private final GastypesRepository gasTypeRepository;
    private final GasTypeMapper gasTypeMapper;

    public GasTypeService(GastypesRepository gasTypeRepository, GasTypeMapper gasTypeMapper) {
        this.gasTypeRepository = gasTypeRepository;
        this.gasTypeMapper = gasTypeMapper;
    }

    public ResponseWrapper getGasTypeById(Long id) {
        return new ResponseWrapper(true,"tipos de gasolina",Collections.singletonList(gasTypeRepository.findById(id).get()));
    }

    public ResponseWrapper getAllGasTypes() {
        return new ResponseWrapper(true,"tipos de gas",gasTypeRepository.findAll());
    }

    public ResponseWrapper addGasType(GastypesDTO gasType) {
        Gastypes gasTypeToAdd = gasTypeMapper.mapDtoToEntity(gasType);
        gasTypeRepository.save(gasTypeToAdd);
        return new ResponseWrapper(true,"tipo de gas agregado", Collections.emptyList());
    }

    public ResponseWrapper updateGasType(GastypesDTO gasType) {
        Optional<Gastypes> gasTypeToUpdate = gasTypeRepository.findById(gasType.getId());

        if (gasTypeToUpdate.isEmpty()) {
            return new ResponseWrapper(false,"tipo de gas no encontrado", Collections.emptyList());
        }

        gasTypeToUpdate.get().setName(gasType.getName() != null ? gasType.getName() : gasTypeToUpdate.get().getName());
        gasTypeToUpdate.get().setPrice(gasType.getPrice() != null ? gasType.getPrice() : gasTypeToUpdate.get().getPrice());
        gasTypeToUpdate.get().setDescription(gasType.getDescription() != null ? gasType.getDescription() : gasTypeToUpdate.get().getDescription());
        gasTypeRepository.save(gasTypeToUpdate.get());
        return new ResponseWrapper(true,"tipo de gas actualizado", Collections.emptyList());
    }
}
