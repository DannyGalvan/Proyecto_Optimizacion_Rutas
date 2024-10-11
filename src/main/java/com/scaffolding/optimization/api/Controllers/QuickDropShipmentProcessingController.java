package com.scaffolding.optimization.api.Controllers;


import com.scaffolding.optimization.QuickDropUtils;
import com.scaffolding.optimization.Services.solver.ShipmentService;
import com.scaffolding.optimization.Services.solver.SimplexOptimizationService;
import com.scaffolding.optimization.database.Entities.Response.AssigmentResponseWrapper;
import com.scaffolding.optimization.database.Entities.Response.shipment.ShipmentResponseWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/shipments")
public class QuickDropShipmentProcessingController {

    private final ShipmentService shipmentService;

    public QuickDropShipmentProcessingController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @GetMapping("/createShipments")
    public ResponseEntity<String> shipments() {
        if (LocalTime.now().isAfter(QuickDropUtils.closeTime)) {
            return ResponseEntity.ok(shipmentService.executeShipmentProcessing());
        }
        return ResponseEntity.ok("Las asignaciones solo pueden realizarse después de las 5:00 PM");
    }

    @GetMapping("/getShipments")
    public ResponseEntity<List<ShipmentResponseWrapper>> getShipments() {

      List<ShipmentResponseWrapper> shipments = shipmentService.getShipments();
        if (shipments.isEmpty()) {
            return ResponseEntity.badRequest().body(Collections.singletonList(new ShipmentResponseWrapper("No hay asignaciones disponibles")));
        }
        return ResponseEntity.ok(shipments);
    }
}
