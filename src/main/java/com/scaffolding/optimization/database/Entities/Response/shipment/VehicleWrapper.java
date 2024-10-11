package com.scaffolding.optimization.database.Entities.Response.shipment;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VehicleWrapper {
    private String plateNumber;
    private Long stopLimit;
    private String description;
    private DriverWrapper driver;

    public VehicleWrapper(String plateNumber, Long stopLimit, String description, DriverWrapper driver) {
        this.plateNumber = plateNumber;
        this.stopLimit = stopLimit;
        this.description = description;
        this.driver = driver;
    }
}
