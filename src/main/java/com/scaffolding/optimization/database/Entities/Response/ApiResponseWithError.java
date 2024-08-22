package com.scaffolding.optimization.database.Entities.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseWithError<TEntity,TError> extends ResponseApi<TEntity> {
    private TError error;

    public ApiResponseWithError(boolean success, String message, TEntity data) {
        super(success, message, data);
    }
}
