package com.java.midtermShopWeb.services.cart;

import com.java.midtermShopWeb.dtos.CartDTO;
import com.java.midtermShopWeb.exceptions.DataNotFoundException;
import com.java.midtermShopWeb.responses.carts.ListCartResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface CartService {
    ResponseEntity<?> addProductToCart(CartDTO cartDTO,String phoneNumber) throws DataNotFoundException;
    ListCartResponse getAllByPhoneNumber(String phoneNumber) throws DataNotFoundException;
    void RemoveFromCart(Long id) throws Exception;
    String updateQuantity(int quantity,Long cartId) throws DataNotFoundException;
    int quantityByPhoneNumber(String phoneNumber) throws DataNotFoundException;
}
