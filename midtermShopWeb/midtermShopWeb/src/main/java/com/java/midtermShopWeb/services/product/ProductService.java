package com.java.midtermShopWeb.services.product;

import com.java.midtermShopWeb.builders.ProductSearchBuilder;
import com.java.midtermShopWeb.constants.MessageKeys;
import com.java.midtermShopWeb.converter.Converter;
import com.java.midtermShopWeb.dtos.ProductDTO;
import com.java.midtermShopWeb.models.Product;
import com.java.midtermShopWeb.repositories.ProductRepository;
import com.java.midtermShopWeb.repositories.Specifications;
import com.java.midtermShopWeb.responses.product.ProductListResponse;
import com.java.midtermShopWeb.responses.product.ProductResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductService implements  IProductService{
    private final ProductRepository productRepository;
    private final Converter converter;
    @Override
    public ProductListResponse getProductsByCondition(Map<String, Object> conditions, int page, int limit) {
        try{
            int totalPages = 0;
            ProductSearchBuilder productSearchBuilder = converter
                    .convertToProductSearchBuilder(conditions,page,limit);
            Sort.Direction direction = Sort.Direction.DESC;

            if (productSearchBuilder.getOrder() != null) {
                direction = productSearchBuilder.getOrder().equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
            }

            Sort sort;
            if (productSearchBuilder.getSortBy() != null && !productSearchBuilder.getSortBy().isEmpty()) {
                sort = Sort.by(direction, productSearchBuilder.getSortBy());
            } else {
                sort = Sort.unsorted();
            }

            PageRequest pageRequest = PageRequest.of(page, limit, sort);
            ProductListResponse productListResponse = new ProductListResponse();
            List<Product> products ;
//            if(productSearchBuilder.getKeyword()!=null || productSearchBuilder.getCategoryId()!=null){
//                Page<Product> pages = productRepository.searchProductsByConditions(
//                        productSearchBuilder.getCategoryId() !=null ? productSearchBuilder.getCategoryId():null,
//                        productSearchBuilder.getKeyword(),
//                        productSearchBuilder.getPriceFrom()!=null ? productSearchBuilder.getPriceFrom():null,
//                        productSearchBuilder.getPriceTo()!=null ? productSearchBuilder.getPriceTo():null,
//                        pageRequest
//                );
//                products= pages.getContent();
//                totalPages = pages.getTotalPages();
//
//
//            }
//            else{
////                products = productRepository
////                        .customMethod(productSearchBuilder);
////                 //totalPages = productRepository.getTotalItems();
//                return null;
//            }
            Specification<Product> spec = Specifications.searchProductsByConditions(productSearchBuilder);
            Page<Product> pages = productRepository.findAll(spec,pageRequest);
            products= pages.getContent();
            totalPages = pages.getTotalPages();
            List<ProductResponse> productResponses = products
                    .stream()
                    .map(ProductResponse::fromProduct)
                    .toList();
            productListResponse.setProducts(productResponses);
            productListResponse.setTotalPages(totalPages);
            return productListResponse;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }


    @Override
    public ProductResponse createOrUpdateProduct(ProductDTO productDTO) {
       if(productDTO.getId()==null){
           Product newProduct = converter.convertToProduct(productDTO);
           return ProductResponse.fromProduct(productRepository.save(newProduct));
       }
       //update
        Product existingProduct = productRepository.findById(productDTO.getId()).orElseThrow(()->
                new RuntimeException(MessageKeys.PRODUCT_NOT_FOUND));
        return ProductResponse
                .fromProduct(productRepository
                                .saveAndFlush(converter
                                        .convertToProduct(existingProduct, productDTO)));
    }

    @Override
    public ProductListResponse deleteByIds(List<Long> ids) {
        List<ProductResponse> productResponses = productRepository
                .deleteAllByIdIn(ids)
                .stream()
                .map(ProductResponse::fromProduct).toList();

        return ProductListResponse.builder()
              .products(productResponses)
              .build();
    }

    @Override
    public ProductResponse getOneById(Long id) {
        return ProductResponse.fromProduct(productRepository
                .findById(id)
                .orElseThrow(()-> new RuntimeException(MessageKeys.PRODUCT_NOT_FOUND))) ;
    }

    @Override
    public ProductResponse addProductsToCart(Long CartId, Long productId, int quantity) {
        return null;
    }

    @Transactional
    @Override
    public String deleteById(Long id) {
        productRepository.deleteById(id);
        return MessageKeys.DELETE_PRODUCT_SUCCESSFULLY;
    }
}
