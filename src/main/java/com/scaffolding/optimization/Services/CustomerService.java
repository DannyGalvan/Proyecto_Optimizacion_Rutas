package com.scaffolding.optimization.Services;

import com.scaffolding.optimization.QuickDropUtils;
import com.scaffolding.optimization.api.AutoMapper.CustomerMapper;
import com.scaffolding.optimization.api.Controllers.CrudServiceProcessingController;
import com.scaffolding.optimization.database.Entities.Response.ResponseWrapper;
import com.scaffolding.optimization.database.Entities.models.Customers;
import com.scaffolding.optimization.database.dtos.CustomersDTO;
import com.scaffolding.optimization.database.repositories.AddressesRepository;
import com.scaffolding.optimization.database.repositories.CustomersRepository;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Service;
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
                .stream().map(customerMapper::mapEntityToDto).toList());

        return responseWrapper;
    }

    @Override
    protected ResponseWrapper validateForCreation(Customers entity) {
        responseWrapper = new ResponseWrapper();
        if (QuickDropUtils.isNullOrEmpty(entity.getFirstName())) {
            responseWrapper.addError("firstName", "el nombre es requerido");
        }

        if (QuickDropUtils.isNullOrEmpty(entity.getLastName())) {
            responseWrapper.addError("lastName", "el apellido es requerido");
        }

        if (!QuickDropUtils.validateCui(entity.getCui())) {
            responseWrapper.addError("cui", "el cui debe de tener 13 digitos");
        }

        if (!QuickDropUtils.validateNit(entity.getNit())) {
            responseWrapper.addError("nit", "el nit debe de tener 9 digitos");
        }

        if (QuickDropUtils.isNullOrEmpty(entity.getPhone())) {
            responseWrapper.addError("phoneNumber", "el telefono es requerido");
        }

        if (!QuickDropUtils.validatePhoneNumber(entity.getPhone())) {
            responseWrapper.addError("phoneNumber", "el telefono debe de tener 8 digitos");
        }
       if(entity.getAddresses().isEmpty()){
           responseWrapper.addError("address", "la direccion es requerida");
         }

        Customers nitExist = customerRepository.findByNit(entity.getNit());
        Customers phoneNumberExist = customerRepository.findByPhone(entity.getPhone());
        Customers cuiExist = customerRepository.findByCui(entity.getCui());

        if (nitExist != null) {
            responseWrapper.addError("nit", "el nit ya existe");
        }

        if (phoneNumberExist != null) {
            responseWrapper.addError("phoneNumber", "el telefono ya existe");
        }

        if (cuiExist != null) {
            responseWrapper.addError("cui", "el cui ya existe");
        }

        ResponseWrapper validationResponseForUser = userService.validateForCreation(entity.getUser());

        if(!validationResponseForUser.getErrors().isEmpty()){
            responseWrapper.getErrors().putAll(validationResponseForUser.getErrors());
        }

        return responseWrapper;
    }

    @Override
    protected ResponseWrapper validateForUpdate(Customers entity) {
        responseWrapper = new ResponseWrapper();
        if (QuickDropUtils.isNullOrEmpty(entity.getFirstName())) {
            responseWrapper.addError("firstName", "el nombre es requerido");
        }

        if (QuickDropUtils.isNullOrEmpty(entity.getLastName())) {
            responseWrapper.addError("lastName", "el apellido es requerido");
        }

        if (QuickDropUtils.isNullOrEmpty(entity.getCui())) {
            responseWrapper.addError("cui", "el cui es requerido");
        }

        if (QuickDropUtils.isObjectNullOrEmpty(entity.getNit())){
            responseWrapper.addError("nit", "el nit es requerido");
        }


        Customers nitExist = customerRepository.findByNit(entity.getNit());
        if (nitExist != null) {
            if (!nitExist.getId().equals(entity.getId())) {
                responseWrapper.addError("nit", "el nit ya existe");
            }
        }

        ResponseWrapper validationResponseForUser = userService.validateForUpdate(entity.getUser());

        if(!validationResponseForUser.getErrors().isEmpty()){
            responseWrapper.getErrors().putAll(validationResponseForUser.getErrors());
        }

        return responseWrapper;
    }

    @Override
    protected ResponseWrapper validateForDelete(Customers entity) {
        responseWrapper = new ResponseWrapper();
        if (entity.getId() == null) {
            responseWrapper.addError("id", "el id es requerido");
        }

        return responseWrapper;
    }

    @Override
    protected ResponseWrapper validateForRead(Customers entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Customers mapToEntityCustomer(CustomersDTO customerDTO) {
        return customerMapper.mapDtoToEntity(customerDTO);
    }
}
