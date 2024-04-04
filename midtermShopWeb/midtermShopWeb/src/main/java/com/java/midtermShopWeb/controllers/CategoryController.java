package com.java.midtermShopWeb.controllers;

import com.java.midtermShopWeb.constants.MessageKeys;
import com.java.midtermShopWeb.dtos.CategoryDTO;
import com.java.midtermShopWeb.models.Category;
import com.java.midtermShopWeb.responses.category.CategoryResponse;
import com.java.midtermShopWeb.responses.category.UpdateCategoryResponse;
import com.java.midtermShopWeb.services.category.CategoryService;
import com.java.midtermShopWeb.utils.LocalizationUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final LocalizationUtils localizationUtils;

    @PostMapping()
    public ResponseEntity<?> createCategory(
            @Valid @RequestBody CategoryDTO categoryDTO,
            BindingResult result
            ) throws Exception {
        CategoryResponse categoryResponse = new CategoryResponse();
        if(result.hasErrors()){
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            categoryResponse.setMessage(localizationUtils.getLocalizedMesage(MessageKeys.INSERT_CATEGORY_FAILED));
            categoryResponse.setErrors(errorMessages);
            return ResponseEntity.badRequest().body(categoryResponse);
        }
        Category category = categoryService.createCategory(categoryDTO);
        categoryResponse.setCategory(category);
        return ResponseEntity.ok().body(categoryResponse);
    }

    @GetMapping()
    public ResponseEntity<?> getAllCategories(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ){
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(
            @PathVariable("id") Long categoryId
    ){
        try{
            Category existingCategory = categoryService.getCategoryById(categoryId);
            return ResponseEntity.ok(existingCategory);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryDTO categoryDTO
    ){
        try{
            UpdateCategoryResponse updateCategoryResponse = new UpdateCategoryResponse();
            categoryService.updateCategory(id,categoryDTO);
            updateCategoryResponse.setMessage(localizationUtils.getLocalizedMesage(localizationUtils.getLocalizedMesage(MessageKeys.UPDATE_CATEGORY_SUCCESSFULLY)));
            return ResponseEntity.ok(updateCategoryResponse);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id){
        try{
            categoryService.deleteCategory(id);
            return ResponseEntity.ok(MessageKeys.DELETE_CATEGORY_SUCCESSFULLY);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
