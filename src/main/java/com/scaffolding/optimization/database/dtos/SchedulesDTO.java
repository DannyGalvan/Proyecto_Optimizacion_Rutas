package com.scaffolding.optimization.database.dtos;


import lombok.Data;

import java.sql.Time;

@Data
public class SchedulesDTO {
    private Long id;

    private Time startTime;

    private Time endTime;

    private String description;

    private Boolean deleted;

}
