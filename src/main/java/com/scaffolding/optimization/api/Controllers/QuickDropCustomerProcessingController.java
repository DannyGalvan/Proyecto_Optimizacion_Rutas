package com.scaffolding.optimization.api.Controllers;


import com.scaffolding.optimization.QuickDropConstants;
import com.scaffolding.optimization.QuickDropUtils;
import com.scaffolding.optimization.Services.CustomerService;
import com.scaffolding.optimization.Services.RoleService;
import com.scaffolding.optimization.database.Entities.Response.ResponseWrapper;
import com.scaffolding.optimization.database.Entities.models.Customers;
import com.scaffolding.optimization.database.Entities.models.Roles;
import com.scaffolding.optimization.database.Entities.models.Users;
import com.scaffolding.optimization.database.dtos.CustomersDTO;
import jakarta.validation.Valid;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Locale;

@RestController
@RequestMapping("/api/v1/customer")
public class QuickDropCustomerProcessingController {
    
    private final RoleService roleService;
    private final CustomerService customerService;

    public QuickDropCustomerProcessingController(RoleService roleServiceCRUD, CustomerService customerService) {
        this.roleService = roleServiceCRUD;
        this.customerService = customerService;
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseWrapper> register(@RequestBody CustomersDTO customerDTO) {
        Users userEntity = new Users();
        Roles role = roleService.findByName(QuickDropConstants.QuickDropRoles.CUSTOMER.getRole());

        userEntity.setAlias(customerDTO.getUser().getAlias());
        userEntity.setEmail(customerDTO.getUser().getEmail().toLowerCase());

        if (ObjectUtils.isEmpty(customerDTO.getUser().getPassword())) {
            String password = QuickDropUtils.generateRandomPassword();
            customerDTO.getUser().setPassword(password);
        }
        userEntity.setPassword(QuickDropUtils.encodePassword(customerDTO.getUser().getPassword()));
        userEntity.setRole(role);

        Customers customerEntity = customerService.mapToEntityCustomer(customerDTO);
        customerEntity.setUser(userEntity);

        customerService.execute(customerEntity, QuickDropConstants.operationTypes.CREATE.getOperationType());

        return ResponseEntity.ok(
                new ResponseWrapper(true,"customer created successfully", Collections.singletonList(null)));
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseWrapper> getAllCustomers() {
        return ResponseEntity.ok(customerService.execute
                (null, QuickDropConstants.operationTypes.READ.getOperationType()));
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseWrapper> updateCustomer(@Valid @RequestBody CustomersDTO customerDTO) {
        return ResponseEntity.ok(customerService.execute(customerService.mapToEntityCustomer(customerDTO),
                QuickDropConstants.operationTypes.UPDATE.getOperationType()));
    }

    @GetMapping("/addresses/{id}")
    public ResponseEntity<ResponseWrapper> getCustomerByID(@PathVariable Long id){
        Customers customer = customerService.findByUserID(id);
        return ResponseEntity.ok(customerService.getAddressesByCustomerId(customer.getId()));
    }
}

