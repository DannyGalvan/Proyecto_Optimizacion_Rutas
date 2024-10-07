package com.scaffolding.optimization.Services;


import com.scaffolding.optimization.api.AutoMapper.CustomerMapper;
import com.scaffolding.optimization.api.Controllers.CrudServiceProcessingController;
import com.scaffolding.optimization.database.Entities.Response.ResponseWrapper;
import com.scaffolding.optimization.database.Entities.models.Customers;
import com.scaffolding.optimization.database.dtos.CustomersDTO;
import com.scaffolding.optimization.database.repositories.AddressesRepository;
import com.scaffolding.optimization.database.repositories.CustomersRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class CustomerService extends CrudServiceProcessingController<Customers> {

    private final CustomersRepository customerRepository;

    private final CustomerMapper customerMapper;
    private final UserService userService;

    private final AddressesRepository addressesRepository;
    private ResponseWrapper responseWrapper;

    public CustomerService(CustomersRepository customerRepository, CustomerMapper customerMapper, UserService userService, AddressesRepository addressesRepository) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.userService = userService;
        this.addressesRepository = addressesRepository;
    }

    @Override
    public ResponseWrapper executeCreation(Customers entity) {
        responseWrapper = new ResponseWrapper();
        userService.executeCreation(entity.getUser());
        Customers customerCreated = customerRepository.save(entity);
        entity.getAddresses().forEach(address -> address.setCustomer(customerCreated));
        addressesRepository.saveAll(entity.getAddresses());
        responseWrapper.setSuccessful(true);
        responseWrapper.setMessage("cliente creado exitosamente");
        return responseWrapper;
    }

    @Override
    public ResponseWrapper executeUpdate(Customers entity) {
        responseWrapper = new ResponseWrapper();
        Optional<Customers> customerEntityFound = customerRepository.findById(entity.getId());
        if (customerEntityFound.isPresent()) {
            customerEntityFound.get().setNit(entity.getNit() != null ? entity.getNit() : customerEntityFound.get().getNit());
            customerEntityFound.get().setFirstName(entity.getFirstName() != null ? entity.getFirstName() : customerEntityFound.get().getFirstName());
            customerEntityFound.get().setLastName(entity.getLastName() != null ? entity.getLastName() : customerEntityFound.get().getLastName());
            customerEntityFound.get().setPhone(entity.getPhone() != null ? entity.getPhone() : customerEntityFound.get().getPhone());
            customerEntityFound.get().setCui(entity.getCui() != null ? entity.getCui() : customerEntityFound.get().getCui());
            customerRepository.save(customerEntityFound.get());
            responseWrapper.setSuccessful(true);
            responseWrapper.setMessage("cliente actualizado exitosamente");
            return responseWrapper;
        }
        responseWrapper.setSuccessful(false);
        responseWrapper.addError("cliente","cliente no encontrado");
        return responseWrapper;
    }

    @Override
    public ResponseWrapper executeDeleteById(Customers entity) {
        responseWrapper = new ResponseWrapper();
        Optional<Customers> customerEntityFound = customerRepository.findById(entity.getId());
        if (customerEntityFound.isPresent()) {
            customerEntityFound.get().getUser().setDeleted(true);
            customerRepository.save(customerEntityFound.get());
            responseWrapper.setSuccessful(true);
            responseWrapper.setMessage("cliente eliminado exitosamente");
            return responseWrapper;
        }
        responseWrapper.setSuccessful(false);
        responseWrapper.addError("cliente","cliente no encontrado");
        return responseWrapper;
    }

    @Override
    public ResponseWrapper executeReadAll() {
        responseWrapper = new ResponseWrapper();
        responseWrapper.setSuccessful(true);
        responseWrapper.setMessage("customers found successfully");
        responseWrapper
                .setData(customerRepository.findAll()
                .stream().filter(customers -> !customers.getUser().getDeleted())
                        .map(customerMapper::mapEntityToDto).toList());

        return responseWrapper;
    }

   public ResponseWrapper getAddressesByCustomerId(Long id){

        Customers customer = customerRepository.findById(id).orElse(null);
        if(customer!=null){
            CustomersDTO customerFound = customerMapper.mapEntityToDto(customer);
            return new ResponseWrapper(true,"customer found", Collections.singletonList(customerFound));
        }
       return new ResponseWrapper(false,"customer not found", Collections.singletonList("data does not exists"));

   }

    public Customers getCustomerById(Long id){
        return customerRepository.findById(id).orElse(null);
    }

    public Customers mapToEntityCustomer(CustomersDTO customerDTO) {
        return customerMapper.mapDtoToEntity(customerDTO);
    }
}
