package com.java.midtermShopWeb.services.order;

import com.java.midtermShopWeb.dtos.OrderDTO;
import com.java.midtermShopWeb.dtos.OrderWithDetailsDTO;
import com.java.midtermShopWeb.exceptions.DataNotFoundException;
import com.java.midtermShopWeb.models.Order;
import com.java.midtermShopWeb.responses.order.OrderListResponse;
import com.java.midtermShopWeb.responses.order.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface OrderService {
    Order createOrder(OrderDTO orderDTO) throws Exception;
    Order getOrder(Long id) throws DataNotFoundException;
    Order updateOrder(Long id, OrderDTO orderDTO) throws DataNotFoundException;
    void deleteOrder(Long id) throws DataNotFoundException;
    List<Order> findByUserId(Long userId);
    Page<Order> getOrdersByKeyword(String keyword, Pageable pageable,String status);
    OrderListResponse getOrdersByConditions(Map<String,Object> conditions);

    OrderListResponse getOrderByPhoneNumber(String phoneNumber,String type) throws DataNotFoundException;
    ResponseEntity<?> createOrderWithDetails (OrderWithDetailsDTO dto,String phoneNumber) throws DataNotFoundException;
    ResponseEntity<?> changingStatus(Long orderId, String status);
    //by matis
}
