package com.scaffolding.optimization.api.Controllers;



import com.scaffolding.optimization.QuickDropConstants;
import com.scaffolding.optimization.Services.ClassificationService;
import com.scaffolding.optimization.database.Entities.Response.ResponseWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/classification")
public class QuickDropClassificationProcessingController {

    private final ClassificationService classificationService;

    public QuickDropClassificationProcessingController(ClassificationService classificationService) {
        this.classificationService = classificationService;
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseWrapper> getAllClassifications() {
        return ResponseEntity.ok(classificationService.execute(null, QuickDropConstants.operationTypes.READ.getOperationType()));
    }
}
