package com.java.midtermShopWeb.controllers;

import com.java.midtermShopWeb.constants.MessageKeys;
import com.java.midtermShopWeb.dtos.ProductDTO;
import com.java.midtermShopWeb.responses.product.ProductListResponse;
import com.java.midtermShopWeb.responses.product.ProductResponse;
import com.java.midtermShopWeb.services.product.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<?> getAllByCondition(
            @RequestParam Map<String,Object> conditions,
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ) {
        try{
            ProductListResponse productListResponse = productService.getProductsByCondition(conditions, page,limit);
            if(productListResponse!=null){
                return ResponseEntity.ok().body(productListResponse);
            }
            return ResponseEntity.badRequest().body(MessageKeys.PRODUCT_NOT_FOUND+" with these conditions");
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductWithId(@PathVariable(value = "id",required = true) Long id){
        try{
            ProductResponse productResponse = productService.getOneById(id);
            if(productResponse!=null){
                return ResponseEntity.ok().body(productResponse);
            }
            return ResponseEntity.badRequest().body(MessageKeys.PRODUCT_NOT_FOUND+ "with id = "+id);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping()
    public ResponseEntity<?> createOrUpdate(@Valid @RequestBody ProductDTO productDTO,
                                            BindingResult result
                                            ){
        try{
            ProductResponse productResponse = new ProductResponse();
            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                productResponse.setErrors(errorMessages);
                return ResponseEntity.badRequest().body(productResponse);
            }
            productResponse = productService.createOrUpdateProduct(productDTO);
            return ResponseEntity.ok().body(productResponse);
        }catch(Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteProductByIds (@RequestBody List<Long> ids){
        if(ids.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(MessageKeys.EMPTY_REQUEST);
        }
        return ResponseEntity.ok().body(productService.deleteByIds(ids));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable("id")Long id){
        try{
            return ResponseEntity.ok().body(productService.deleteById(id));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

