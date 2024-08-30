package com.scaffolding.optimization.api.Controllers;


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
