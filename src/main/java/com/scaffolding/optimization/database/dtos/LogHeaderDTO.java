package com.scaffolding.optimization.database.dtos;


import lombok.Data;

import java.util.Date;

@Data
public class LogHeaderDTO {
    private Long id;

    private Long recordId;

    private String tableName;

    private String operation;

    private Date createdAt;

}
