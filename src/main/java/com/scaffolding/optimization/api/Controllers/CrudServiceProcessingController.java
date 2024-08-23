package com.scaffolding.optimization.api.Controllers;

import com.scaffolding.optimization.database.Entities.Response.ResponseApi;
import com.scaffolding.optimization.database.Entities.Response.ResponseWrapper;

public abstract  class CrudServiceProcessingController<T> implements QuickDropProcessingController<T> {
    private final String UPDATE_OPERATION = "UPDATE";
    private final String DELETE_OPERATION = "DELETE";
    private final String CREATE_OPERATION = "CREATE";
    private final String READ_OPERATION = "READ";

    public abstract ResponseWrapper executeCreation(T entity);

    public abstract ResponseWrapper executeUpdate(T entity);

    public abstract ResponseWrapper executeDeleteById(T entity);

    public abstract ResponseWrapper executeReadAll();

    protected abstract ResponseWrapper validateForCreation(T entity);

    protected abstract ResponseWrapper validateForUpdate(T entity);

    protected abstract ResponseWrapper validateForDelete(T entity);

    protected abstract ResponseWrapper validateForRead(T entity);

    @Override
    public ResponseWrapper validate(T entity, String operation) {

        if (entity == null) {
            ResponseApi<T> responseWrapper = new ResponseApi<T>();
        }

        return switch (operation) {
            case CREATE_OPERATION -> validateForCreation(entity);
            case UPDATE_OPERATION -> validateForUpdate(entity);
            case DELETE_OPERATION -> validateForDelete(entity);
            case READ_OPERATION -> validateForRead(entity);
            default -> throw new RuntimeException("Operation not found");
        };
    }

    @Override
    public ResponseWrapper execute(T entity, String operation) {
        return switch (operation) {
            case CREATE_OPERATION -> executeCreation(entity);
            case UPDATE_OPERATION -> executeUpdate(entity);
            case DELETE_OPERATION -> executeDeleteById(entity);
            case READ_OPERATION -> executeReadAll();
            default -> throw new RuntimeException("Operation not found");
        };
    }
}
