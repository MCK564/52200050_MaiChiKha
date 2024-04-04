package com.java.midtermShopWeb.controllers;

import com.java.midtermShopWeb.constants.MessageKeys;
import com.java.midtermShopWeb.dtos.ChangingStatusDTO;
import com.java.midtermShopWeb.dtos.OrderWithDetailsDTO;
import com.java.midtermShopWeb.responses.order.OrderListResponse;
import com.java.midtermShopWeb.responses.order.OrderResponse;
import com.java.midtermShopWeb.services.order.OrderService;
import com.java.midtermShopWeb.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final JwtUtils jwtUtils;


    @GetMapping("/search")
    public ResponseEntity<?> getAllOrderByCondition(
            @RequestParam String keyword,
            @RequestParam String status,
            @RequestParam int page,
            @RequestParam int limit){
        try{
            PageRequest pageRequest = PageRequest.of(
                    page, limit,
                    Sort.by("id").ascending()
            );
            Page<OrderResponse> orderPage = orderService
                    .getOrdersByKeyword(keyword, pageRequest,status)
                    .map(OrderResponse::fromOrder);

            int totalPages = orderPage.getTotalPages();
            List<OrderResponse> orderResponses = orderPage.getContent();
            return ResponseEntity.ok(OrderListResponse
                    .builder()
                    .orders(orderResponses)
                    .totalPages(totalPages)
                    .build());
        }catch(Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }



    @GetMapping("/{type}")
    public ResponseEntity<?> getOrderByUserAndType(
            @PathVariable("type")String type,
            HttpServletRequest request)
    {
        try{
            final String authHeader = request.getHeader("Authorization");
            if(authHeader == null || !authHeader.startsWith("Bearer ")){
                return ResponseEntity.status(401).body(MessageKeys.UNAUTHORIZED);
            }
            final String token = authHeader.substring(7);
            final String phoneNumber = jwtUtils.extractPhoneNumber(token);
            return ResponseEntity.ok().body(orderService.getOrderByPhoneNumber(phoneNumber,type));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeOrder(@PathVariable("id")Long id){
        try{
            orderService.deleteOrder(id);
            return ResponseEntity.ok().body(MessageKeys.DELETE_ORDER_SUCCESSFULLY);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping()
    public ResponseEntity<?> createOrUpdateOrder(
            @RequestBody OrderWithDetailsDTO dto,
            HttpServletRequest request
    ){
        try{
            final String authHeader = request.getHeader("Authorization");
            if(authHeader == null || !authHeader.startsWith("Bearer ")){
                return ResponseEntity.status(401).body(MessageKeys.UNAUTHORIZED);
            }
            final String token = authHeader.substring(7);
            final String phoneNumber = jwtUtils.extractPhoneNumber(token);
            return orderService.createOrderWithDetails(dto,phoneNumber);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/changing-status")
    public ResponseEntity<?> pendingOrder (
            @RequestBody ChangingStatusDTO dto
            ){
        try{
            return orderService.changingStatus(dto.getId(), dto.getStatus());
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
