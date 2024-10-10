package com.scaffolding.optimization.api.Controllers;


import com.scaffolding.optimization.Services.solver.SimplexOptimizationService;
import com.scaffolding.optimization.database.Entities.models.Orders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/shipments")
public class QuickDropShipmentProcessingController {

    private final SimplexOptimizationService simplexOptimizationService;

    public QuickDropShipmentProcessingController(SimplexOptimizationService simplexOptimizationService) {
        this.simplexOptimizationService = simplexOptimizationService;
    }

//    @GetMapping
//    public ResponseEntity<String> processShipments() {
//        return ResponseEntity.ok(simplexOptimizationService.minimizeCost());
//    }

    @GetMapping("/assigments")
    public ResponseEntity<Map<Orders, String>> processAssigments() {
        return ResponseEntity.ok(simplexOptimizationService.assignVehiclesToOrders());
    }
}
