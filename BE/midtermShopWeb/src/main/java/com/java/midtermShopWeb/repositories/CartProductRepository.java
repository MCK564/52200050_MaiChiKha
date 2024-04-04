package com.java.midtermShopWeb.repositories;

import com.java.midtermShopWeb.models.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartProductRepository extends JpaRepository<CartProduct,Long> {
    List<CartProduct> findAllByUserId(Long id);
    CartProduct findByProductIdAndUserId(Long productId,Long userId);
}
