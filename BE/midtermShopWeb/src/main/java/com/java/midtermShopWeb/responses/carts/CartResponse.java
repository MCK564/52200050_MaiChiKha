package com.java.midtermShopWeb.responses.carts;

import com.java.midtermShopWeb.models.CartProduct;
import com.java.midtermShopWeb.responses.product.ProductResponse;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartResponse {

    private Long id;
    private ProductResponse productResponse;
    private int quantity;
    private String message;
    private List<String> errors = new ArrayList<>();

    public static CartResponse fromCartProduct(CartProduct cp){
        return CartResponse.builder()
                .id(cp.getId())
                .productResponse(ProductResponse.fromProduct(cp.getProduct()))
                .quantity(cp.getQuantity())
                .build();
    }

}
