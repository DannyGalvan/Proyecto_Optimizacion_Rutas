package com.scaffolding.optimization.api.Controllers;

import com.scaffolding.optimization.Services.ProductService;
import com.scaffolding.optimization.api.converter.DTOParser;
import com.scaffolding.optimization.database.Entities.Response.ResponseWrapper;
import com.scaffolding.optimization.database.Entities.models.Products;
import com.scaffolding.optimization.database.dtos.ProductsDTO;
import com.scaffolding.optimization.database.repositories.ClassificationsRepository;
import com.scaffolding.optimization.database.repositories.SuppliersRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/products")
public class QuickDropProductProcessingController {

    private final DTOParser dtoParser;
    private final ProductService productService;

    private final ClassificationsRepository classificationsRepository;
    private final SuppliersRepository suppliersRepository;

    public QuickDropProductProcessingController(DTOParser dtoParser, ProductService productService, ClassificationsRepository classificationsRepository,
                                                SuppliersRepository suppliersRepository) {
        this.dtoParser = dtoParser;
        this.productService = productService;
        this.classificationsRepository = classificationsRepository;
        this.suppliersRepository = suppliersRepository;
    }

    @PostMapping("/upload")
    public ResponseEntity<ResponseWrapper> uploadProducts(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(
                    new ResponseWrapper(false, "Please upload a file!", null));
        }

        try {
            Path tempFile = Files.createTempFile("upload-", file.getOriginalFilename());
            Files.copy(file.getInputStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);

            File inputFile = tempFile.toFile();
            List<ProductsDTO> products = dtoParser.parse(ProductsDTO.class, inputFile);
            Files.delete(tempFile);


            List<ProductsDTO> validProducts = products.stream()
                    .filter(product -> suppliersRepository.existsById(product.getSupplierId()))
                    .filter(product -> classificationsRepository.existsById(product.getClassificationId()))
                    .filter(product -> !productService.existsByNames(product.getName()))
                    .toList();

            if (validProducts.isEmpty()) {
                return ResponseEntity.badRequest().body(
                        new ResponseWrapper(false, "the products already exist or the supplier does not exist", null));
            }

            Map<Long, ProductsDTO> dtoMap = validProducts.stream()
                    .collect(Collectors.toMap(ProductsDTO::getId, dto -> dto));

            List<Products> productsEntities = validProducts.stream()
                    .map(productService::mapDtoToEntity)
                    .peek(product -> {
                        ProductsDTO dto = dtoMap.get(product.getId());
                        if (dto == null) {
                            throw new RuntimeException("Product DTO not found");
                        }
                        product.setDeleted(false);
                        product.setSupplier(suppliersRepository.findById(dto.getSupplierId())
                                .orElseThrow(() -> new RuntimeException("Supplier not found")));
                        product.setClassification(classificationsRepository.findById(dto.getClassificationId())
                                .orElseThrow(() -> new RuntimeException("Classification not found")));
                    })
                    .toList();

            productsEntities.forEach(productService::executeCreation);

            return ResponseEntity.ok(new ResponseWrapper(true, "Products uploaded successfully",
                    Collections.singletonList("Numbers of products uploaded: " + validProducts.size())));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new ResponseWrapper(false, "Error processing file", null));
        }
    }

    @GetMapping("/filter")
    public ResponseEntity<ResponseWrapper> filterProducts(@RequestParam(required = false) String search,
            @RequestParam(required = false) Integer classifyId, @RequestParam(required = false) Integer supplierId) {
        

        return ResponseEntity.ok(new ResponseWrapper(true, "Products filtered successfully",
                productService.executeDynamicQuery(search, classifyId, supplierId)));
    }

}
