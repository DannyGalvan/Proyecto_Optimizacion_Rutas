package com.scaffolding.optimization.database.Entities.Response.shipment;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomerWrapper {
    private String name;
    private String phone;
    private String addressName;
    private String latitude;
    private String longitude;

    public CustomerWrapper(String name, String phone, String addressName, String latitude, String longitude) {
        this.name = name;
        this.phone = phone;
        this.addressName = addressName;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
