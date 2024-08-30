package com.scaffolding.optimization.Services;



import com.scaffolding.optimization.api.AutoMapper.ClassificationMapper;
import com.scaffolding.optimization.api.Controllers.CrudServiceProcessingController;
import com.scaffolding.optimization.database.Entities.Response.ResponseWrapper;
import com.scaffolding.optimization.database.Entities.models.Classifications;
import com.scaffolding.optimization.database.dtos.ClassificationsDTO;
import com.scaffolding.optimization.database.repositories.ClassificationsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassificationService extends CrudServiceProcessingController<Classifications> {

    private final ClassificationsRepository classificationsRepository;
    private final ClassificationMapper classificationMapper;

    public ClassificationService(ClassificationsRepository classificationsRepository, ClassificationMapper classificationMapper) {
        this.classificationsRepository = classificationsRepository;
        this.classificationMapper = classificationMapper;
    }

    @Override
    public ResponseWrapper executeCreation(Classifications entity) {

        return new ResponseWrapper(true, "Classification created successfully", null);
    }

    @Override
    public ResponseWrapper executeUpdate(Classifications entity) {
        return null;
    }

    @Override
    public ResponseWrapper executeDeleteById(Classifications entity) {
        return null;
    }

    @Override
    public ResponseWrapper executeReadAll() {
        List<ClassificationsDTO> classifications = classificationsRepository.findAll()
                .stream()
                .map(classificationMapper::mapEntityToDto)
                .toList();
        return new ResponseWrapper(true, "Classifications found", classifications);
    }
}
