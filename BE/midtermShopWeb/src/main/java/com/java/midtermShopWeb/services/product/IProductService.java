package com.java.midtermShopWeb.services.product;

import com.java.midtermShopWeb.dtos.ProductDTO;
import com.java.midtermShopWeb.responses.product.ProductListResponse;
import com.java.midtermShopWeb.responses.product.ProductResponse;

import java.util.List;
import java.util.Map;


public interface IProductService {
    ProductListResponse getProductsByCondition(Map<String,Object> conditions, int page, int limit);
    ProductResponse createOrUpdateProduct(ProductDTO productDTO);
    ProductListResponse deleteByIds (List<Long> ids);
    ProductResponse getOneById(Long id);
    ProductResponse addProductsToCart(Long CartId, Long productId, int quantity);
    String deleteById(Long id);

}
