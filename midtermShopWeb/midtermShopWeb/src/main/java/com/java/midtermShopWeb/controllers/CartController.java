package com.java.midtermShopWeb.controllers;

import com.java.midtermShopWeb.constants.MessageKeys;
import com.java.midtermShopWeb.dtos.CartDTO;
import com.java.midtermShopWeb.services.cart.CartService;
import com.java.midtermShopWeb.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/carts")
@RequiredArgsConstructor
@Validated
public class CartController {
    private final CartService cartService;
    private final JwtUtils jwtUtils;

    @GetMapping("")
    public ResponseEntity<?> getAllProductInCartByUserId(
            HttpServletRequest request
    )
    {
        try{
            final String authHeader = request.getHeader("Authorization");
            if(authHeader == null || !authHeader.startsWith("Bearer ")){
                return ResponseEntity.status(401).body(MessageKeys.UNAUTHORIZED);
            }
            final String token = authHeader.substring(7);
            final String phoneNumber = jwtUtils.extractPhoneNumber(token);
            return ResponseEntity.ok().body(cartService.getAllByPhoneNumber(phoneNumber));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("")
    public ResponseEntity<?> putProductIntoCart(
            @Valid @RequestBody CartDTO cartDTO,
            HttpServletRequest request,
            BindingResult result
            ){
        try{
            final String authHeader = request.getHeader("Authorization");
            if(authHeader == null || !authHeader.startsWith("Bearer ")){
                return ResponseEntity.status(401).body(MessageKeys.UNAUTHORIZED);
            }
            final String token = authHeader.substring(7);
            final String phoneNumber = jwtUtils.extractPhoneNumber(token);
            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            return cartService.addProductToCart(cartDTO,phoneNumber);
        }catch(Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductInCart(@PathVariable(value = "id",required = true)Long id){
        try{
            cartService.RemoveFromCart(id);
            return ResponseEntity.ok().body(MessageKeys.DELETE_CART_SUCCESSFULLY);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateQuantity(
            @PathVariable("id")Long cartId,
            @RequestBody Integer quantity
    ){
        try{
            return ResponseEntity.ok(cartService.updateQuantity(quantity,cartId));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @GetMapping("/quantity")
    public ResponseEntity<?> getCartProductNumbers (HttpServletRequest
                                                    request){
        try{
            final String authHeader = request.getHeader("Authorization");
            if(authHeader == null || !authHeader.startsWith("Bearer ")){
                return ResponseEntity.status(401).body(MessageKeys.UNAUTHORIZED);
            }
            final String token = authHeader.substring(7);
            final String phoneNumber = jwtUtils.extractPhoneNumber(token);
            return ResponseEntity.ok().body(cartService.quantityByPhoneNumber(phoneNumber));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
