package com.scaffolding.optimization.database.Entities.Response.shipment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DriverWrapper {
    private String name;
    private String phone;
    private String schedule;

    public DriverWrapper(String name, String phone, String schedule) {
        this.name = name;
        this.phone = phone;
        this.schedule = schedule;
    }
}
