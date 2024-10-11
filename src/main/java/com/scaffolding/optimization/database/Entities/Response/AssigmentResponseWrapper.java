package com.scaffolding.optimization.database.Entities.Response;

import com.scaffolding.optimization.database.dtos.OrdersDTO;
import com.scaffolding.optimization.database.dtos.VehiclesDTO;
import com.scaffolding.optimization.database.dtos.WarehousesDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
@NoArgsConstructor
public class AssigmentResponseWrapper {
    private String message;
    private OrdersDTO order;
    private List<WarehousesDTO> warehouses;
    private VehiclesDTO vehicle;

    public AssigmentResponseWrapper(String message) {
        this.message = message;
    }
}
