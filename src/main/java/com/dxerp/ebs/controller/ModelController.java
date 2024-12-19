package com.dxerp.ebs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dxerp.ebs.service.ModelService;
import com.dxerp.ebs.dto.ModelDTO;
import com.dxerp.ebs.entity.Model;

import com.dxerp.ebs.util.ApiResponse;

@RestController
@RequestMapping("/api/models")
public class ModelController {
    @Autowired
    private ModelService modelService;
       public ModelController(ModelService modelService) {
        this.modelService = modelService;
    }

     @PostMapping("/create")
      @PreAuthorize("hasPermission(null, 'model/create')")
      public ResponseEntity<ApiResponse<Model>> registerUser(@RequestBody ModelDTO modelDTO) {
         ApiResponse<Model> response = modelService.createModel(modelDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<ApiResponse<List<Model>>> getModelsDropdown() {
     
        List<Model> models = modelService.getAllModels();
        return ResponseEntity.ok(new ApiResponse<>(true, "Products fetched successfully", models));
    }

    @GetMapping("/list")
    @PreAuthorize("hasPermission(null, 'model/list')")
    public ResponseEntity<ApiResponse<List<Model>>> getAllModels() {
     
        List<Model> models = modelService.getAllModels();
        return ResponseEntity.ok(new ApiResponse<>(true, "Models fetched successfully", models));
    }

        @GetMapping("/{id}")
    public ResponseEntity<ModelDTO> getUserById(@PathVariable Long id) {
        ModelDTO modelDTO = modelService.getModelById(id);
        return ResponseEntity.ok(modelDTO);
    }

     @PutMapping("/{id}")
     @PreAuthorize("hasPermission(null, 'model/edit')")
     public ResponseEntity<ApiResponse<Model>> updateModel(@PathVariable Long id, @RequestBody ModelDTO modelDTO) {
        Model updatedModel = modelService.updateModel(id, modelDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "Model updated successfully", updatedModel));
    }

      @DeleteMapping("/{id}")
     @PreAuthorize("hasPermission(null, 'model/delete')")
    public ResponseEntity<ApiResponse<Void>> deleteModel(@PathVariable Long id) {
        modelService.deleteModel(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Model deleted successfully", null));
    }
    
}
