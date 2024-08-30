package com.scaffolding.optimization.api.Controllers;

import com.scaffolding.optimization.Services.GasTypeService;
import com.scaffolding.optimization.database.Entities.Response.ResponseWrapper;
import com.scaffolding.optimization.database.dtos.GastypesDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/gas-price")
public class QuickDropGasTypeProcessingController {

    private final GasTypeService gasTypeService;

    public QuickDropGasTypeProcessingController(GasTypeService gasTypeService) {
        this.gasTypeService = gasTypeService;
    }

    @GetMapping("/all")
    public ResponseWrapper getGasPrice() {
        return gasTypeService.getAllGasTypes();
    }

    @PostMapping("/add")
    public ResponseWrapper addGasPrice(@RequestBody GastypesDTO gasType) {
        return gasTypeService.addGasType(gasType);
    }

    @GetMapping("/{id}")
    public ResponseWrapper getGasPriceById(@PathVariable Long id) {
        return gasTypeService.getGasTypeById(id);
    }

    @PutMapping("/update")
    public ResponseWrapper updateGasPrice(@RequestBody GastypesDTO gasType) {
        return gasTypeService.updateGasType(gasType);
    }
}
