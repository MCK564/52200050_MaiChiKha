package com.java.midtermShopWeb.services.cart;

import com.java.midtermShopWeb.builders.ProductSearchBuilder;
import com.java.midtermShopWeb.constants.MessageKeys;
import com.java.midtermShopWeb.dtos.CartDTO;
import com.java.midtermShopWeb.exceptions.DataNotFoundException;
import com.java.midtermShopWeb.models.CartProduct;
import com.java.midtermShopWeb.models.Product;
import com.java.midtermShopWeb.models.User;
import com.java.midtermShopWeb.repositories.CartProductRepository;
import com.java.midtermShopWeb.repositories.ProductRepository;
import com.java.midtermShopWeb.repositories.UserRepository;
import com.java.midtermShopWeb.responses.carts.CartResponse;
import com.java.midtermShopWeb.responses.carts.ListCartResponse;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ICartService implements CartService{
    private final UserRepository userRepository;
    private final CartProductRepository cartProductRepository;
    private final ProductRepository productRepository;

    @Override
    public ResponseEntity<?> addProductToCart(CartDTO cartDTO,String phoneNumber) throws DataNotFoundException {
       try{
           Long productId = cartDTO.getProductId();
           int quantity = cartDTO.getQuantity();
           User existingUser = userRepository.findByPhoneNumber(phoneNumber).orElseThrow(()
                   -> new DataNotFoundException(MessageKeys.DATA_NOT_FOUND));
           Product existingProduct = productRepository.findById(productId).orElseThrow(()->
                   new DataNotFoundException(MessageKeys.PRODUCT_NOT_FOUND+productId));

           //check xem có sản phẩm trong giỏ hàng rồi thì cập nhật
           CartProduct existingCartProduct = cartProductRepository.findByProductIdAndUserId(productId,existingUser.getId());
           if(existingCartProduct!=null){
               existingCartProduct.setQuantity(quantity);
               return ResponseEntity.ok().body(
                       CartResponse.fromCartProduct(cartProductRepository.saveAndFlush(existingCartProduct)));
           }
            else{
               CartProduct cartProduct = CartProduct.builder()
                       .product(existingProduct)
                       .user(existingUser)
                       .quantity(quantity)
                       .build();
               return ResponseEntity.ok().body(CartResponse.fromCartProduct(cartProductRepository.saveAndFlush(cartProduct)));
           }

       }catch(Exception e){
           return ResponseEntity.internalServerError().body(e.getMessage());
       }

    }

    @Override
    public ListCartResponse getAllByPhoneNumber(String phoneNumber) throws DataNotFoundException {
        User existingUser = userRepository.findByPhoneNumber(phoneNumber).orElseThrow(()
                -> new DataNotFoundException(MessageKeys.DATA_NOT_FOUND));
        List<CartProduct> cartProducts = cartProductRepository.findAllByUserId(existingUser.getId());
        if(cartProducts.isEmpty()){
            return null;
        }
        List<CartResponse> cartResponses = cartProducts.stream().map(CartResponse::fromCartProduct).toList();
        return ListCartResponse.builder()
                .cartResponses(cartResponses)
                .totalPages(cartResponses.size()/20+1)
                .quantity(cartProducts.size())
                .build();
    }

    @Override
    public void RemoveFromCart(Long id) throws Exception {
       if(cartProductRepository.existsById(id)){
          cartProductRepository.deleteById(id);
       }
       else {throw new DataNotFoundException("can not find cart product with id= "+id);}
    }

    @Override
    public String updateQuantity(int quantity, Long cartId) throws DataNotFoundException {
       CartProduct existingCartProduct = cartProductRepository.findById(cartId)
               .orElseThrow(()->new DataNotFoundException(MessageKeys.DATA_NOT_FOUND));

       existingCartProduct.setQuantity(quantity);
       if(cartProductRepository.saveAndFlush(existingCartProduct)!=null){
           return MessageKeys.UPDATE_SUCCESSFULLY;
       }
       return MessageKeys.UPDATE_FAILED;
    }

    @Override
    public int quantityByPhoneNumber(String phoneNumber) throws DataNotFoundException {
        User existingUser = userRepository.findByPhoneNumber(phoneNumber).orElseThrow(()
                -> new DataNotFoundException(MessageKeys.DATA_NOT_FOUND));
        List<CartProduct> cartProducts = cartProductRepository.findAllByUserId(existingUser.getId());
        return cartProducts.size();
    }


}
