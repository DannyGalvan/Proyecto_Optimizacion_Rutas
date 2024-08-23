package com.scaffolding.optimization.api.Controllers;

import com.scaffolding.optimization.database.Entities.Response.ResponseWrapper;

public interface  QuickDropProcessingController<T> {
    ResponseWrapper execute(T entity, String operation);
    ResponseWrapper validate(T entity, String operation);
}
