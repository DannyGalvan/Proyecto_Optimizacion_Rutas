package com.scaffolding.optimization.database.Entities.Response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "API Response Entity")
public class ResponseApi<TEntity> {
    private boolean success;
    private String message;
    private TEntity data;
}
