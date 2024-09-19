package com.scaffolding.optimization.api.Controllers;

import com.scaffolding.optimization.QuickDropUtils;
import com.scaffolding.optimization.Services.SupplierService;
import com.scaffolding.optimization.api.converter.DTOParser;
import com.scaffolding.optimization.database.Entities.Response.ResponseWrapper;
import com.scaffolding.optimization.database.dtos.SuppliersDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/supplier")
public class SupplierProcessingControl {

    private final DTOParser dtoParser;
    private final SupplierService supplierService;

    public SupplierProcessingControl(DTOParser dtoParser, SupplierService supplierService) {
        this.dtoParser = dtoParser;
        this.supplierService = supplierService;
    }

    @PostMapping("/upload")
    public ResponseEntity<ResponseWrapper> uploadSuppliers(@RequestParam("file") MultipartFile file) {

        if (QuickDropUtils.isFileEmpty(file)) {
            return ResponseEntity.badRequest().body(
                    new ResponseWrapper(false, "Please upload a file!", null));
        }

        try {
            Path tempFile = Files.createTempFile("upload-", file.getOriginalFilename());
            Files.copy(file.getInputStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);

            File inputFile = tempFile.toFile();
            List<SuppliersDTO> products = dtoParser.parse(SuppliersDTO.class, inputFile);
            Files.delete(tempFile);

            List<SuppliersDTO> validSuppliers = products.stream()
                    .filter(product -> !supplierService.existsByNames(product.getName()))
                    .toList();

            if (validSuppliers.isEmpty()) {
                return ResponseEntity.badRequest().body(new ResponseWrapper(false, "suppliers already exist", null));
            }

            validSuppliers.stream()
                    .map(supplierService::mapDtoToEntity)
                    .forEach(supplierService::executeCreation);


            return ResponseEntity.ok(new ResponseWrapper(true, "Suppliers uploaded successfully",
                    Collections.singletonList("Numbers of suppliers uploaded: " + validSuppliers.size())));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new ResponseWrapper(false, "Error processing file", null));
        }
    }
}
