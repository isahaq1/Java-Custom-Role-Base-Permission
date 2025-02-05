package com.dxerp.ebs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;

import com.dxerp.ebs.dto.ModelDTO;
import com.dxerp.ebs.entity.Model;
import com.dxerp.ebs.entity.User;

import org.springframework.web.bind.annotation.*;
import com.dxerp.ebs.repository.ModelRepository;
import com.dxerp.ebs.util.ApiResponse;
import org.springframework.stereotype.Service;

@Service
public class ModelService {
    @Autowired
    private ModelRepository modelRepository;

    public ApiResponse<Model> createModel(ModelDTO modelDTO) {
        Model model = new Model();
        model.setName(modelDTO.getName());
        modelRepository.save(model);

        return new ApiResponse<>(true, "model Successfully Created", model);
    }

    public List<Model> getAllModels() {
       return  modelRepository.findAll();
        
    }

    @PreAuthorize("hasPermission(null, 'model/delete')")
    public void deleteModel(Long id) {
        Model model = modelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Model not found"));
        modelRepository.delete(model);
    }

    public Model updateModel(Long id, ModelDTO modelDTO) {
        Model model = modelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("model not found"));
              
        model.setName(modelDTO.getName());
        model.setStatus(modelDTO.getStatus());
        return modelRepository.save(model);
    }

    public ModelDTO getModelById(Long id) {
        Model model = modelRepository.findById(id).orElseThrow(() -> new RuntimeException("Model not found"));
        ModelDTO modelDTO = new ModelDTO();
        modelDTO.setId(model.getId());
        modelDTO.setName(model.getName());
        modelDTO.setStatus(model.getStatus());
        return modelDTO;
        
    }

      @PreAuthorize("hasPermission(null, 'models/list')")
    public Page<Model> getAllModelList(int page, int size) {
        return modelRepository.findAll(PageRequest.of(page, size));
    }




    
}
