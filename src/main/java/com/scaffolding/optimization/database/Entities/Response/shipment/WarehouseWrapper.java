package com.scaffolding.optimization.database.Entities.Response.shipment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WarehouseWrapper {
    private String addressName;
    private String latitude;
    private String longitude;

    public WarehouseWrapper(String addressName, String latitude, String longitude) {
        this.addressName = addressName;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
