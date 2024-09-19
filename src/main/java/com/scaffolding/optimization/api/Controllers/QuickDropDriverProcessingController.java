package com.scaffolding.optimization.api.Controllers;


import com.scaffolding.optimization.QuickDropConstants;
import com.scaffolding.optimization.QuickDropUtils;
import com.scaffolding.optimization.Services.DriverService;
import com.scaffolding.optimization.Services.RoleService;
import com.scaffolding.optimization.database.Entities.Response.ResponseWrapper;
import com.scaffolding.optimization.database.Entities.models.Drivers;
import com.scaffolding.optimization.database.Entities.models.Roles;
import com.scaffolding.optimization.database.Entities.models.Users;
import com.scaffolding.optimization.database.dtos.DriversDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/v1/driver")
public class QuickDropDriverProcessingController {

    private final RoleService roleService;
    private final DriverService driverService;

    public QuickDropDriverProcessingController(RoleService roleService, DriverService driverService) {
        this.roleService = roleService;
        this.driverService = driverService;
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseWrapper> register(@Valid @RequestBody DriversDTO driverDTO) {
        Users userEntity = new Users();
        Roles role = roleService.findByName(QuickDropConstants.QuickDropRoles.DRIVER.getRole());

        userEntity.setAlias(driverDTO.getUser().getAlias());
        userEntity.setEmail(driverDTO.getUser().getEmail());
        userEntity.setPassword(QuickDropUtils.encodePassword(driverDTO.getUser().getPassword()));
        userEntity.setRole(role);

        Drivers driver = driverService.mapToEntityDriver(driverDTO);
        driver.setUser(userEntity);

        driverService.execute(driver, QuickDropConstants.operationTypes.CREATE.getOperationType());

        return ResponseEntity.ok(
                new ResponseWrapper(true,"customer created successfully", Collections.singletonList(null)));
    }

    @PostMapping("/all")
    public ResponseEntity<ResponseWrapper> getAllDrivers() {
        return ResponseEntity.ok(driverService.execute
                (null, QuickDropConstants.operationTypes.READ.getOperationType()));
    }
}
