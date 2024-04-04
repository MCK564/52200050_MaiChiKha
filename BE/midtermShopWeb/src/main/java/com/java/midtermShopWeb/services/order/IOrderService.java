package com.java.midtermShopWeb.services.order;

import com.java.midtermShopWeb.constants.MessageKeys;
import com.java.midtermShopWeb.dtos.OrderDTO;
import com.java.midtermShopWeb.dtos.OrderWithDetailsDTO;
import com.java.midtermShopWeb.exceptions.DataNotFoundException;
import com.java.midtermShopWeb.mappers.OrderMapper;
import com.java.midtermShopWeb.models.CartProduct;
import com.java.midtermShopWeb.models.Order;
import com.java.midtermShopWeb.models.OrderDetail;
import com.java.midtermShopWeb.models.User;
import com.java.midtermShopWeb.repositories.*;
import com.java.midtermShopWeb.responses.order.OrderListResponse;
import com.java.midtermShopWeb.responses.order.OrderResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IOrderService implements OrderService{
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final CartProductRepository cartProductRepository;
    private final OrderDetailRepository orderDetailRepository;
//    private final OrderMapper orderMapper;

    @Override
    public Order createOrder(OrderDTO orderDTO) throws Exception {
        return null;
    }

    @Override
    public Order getOrder(Long id) throws DataNotFoundException {
        return orderRepository.findById(id).orElseThrow(()
        -> new DataNotFoundException(MessageKeys.DATA_NOT_FOUND));
    }

    @Override
    public Order updateOrder(Long id, OrderDTO orderDTO) throws DataNotFoundException {
        return null;
    }

    @Override
    public void deleteOrder(Long id) throws DataNotFoundException {
        Order existingOrder = orderRepository.findById(id).orElseThrow(()
                -> new DataNotFoundException(MessageKeys.DATA_NOT_FOUND));
        orderRepository.delete(existingOrder);
    }

    @Override
    public List<Order> findByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public Page<Order> getOrdersByKeyword(String keyword, Pageable pageable, String status) {
        if(!status.isEmpty()){
            return orderRepository.findByKeywordAndStatus(keyword, pageable,status);
        }
        return orderRepository.findByKeyword(keyword, pageable);
    }

    @Override
    public OrderListResponse getOrdersByConditions(Map<String, Object> conditions) {
      try{
          List<Order> orders;
          if(conditions.isEmpty()){
              orders = orderRepository.findAll();
          }
          else{
              Map<String, Object> params = new HashMap<>(conditions); // Chuyển các điều kiện vào params
//              orders = orderMapper.findOrderByParams(params);
          }
//          List<OrderResponse> orderResponses = orders.stream().map(OrderResponse::fromOrder).toList();
//          return OrderListResponse.builder()
//                  .orders(orderResponses)
//                  .totalPages(orders.size()/20)
//                  .build();
          return null;
      }catch(Exception e){
          return null;
      }
    }

    @Override
    public OrderListResponse getOrderByPhoneNumber(String phoneNumber,String type) throws DataNotFoundException {
        User existingUser = userRepository.findByPhoneNumber(phoneNumber).orElseThrow(()
        -> new DataNotFoundException(MessageKeys.DATA_NOT_FOUND));
        List<Order> orders;
        if(type.equals("all")){
            orders = orderRepository.findByUserId(existingUser.getId());
        }
       else{
            orders = orderRepository.findAllByStatusAndUser_Id(type,existingUser.getId());
        }

        List<OrderResponse> orderResponses = orders.stream().map(OrderResponse::fromOrder).toList();
        return OrderListResponse.builder()
                .orders(orderResponses)
                .totalPages(orders.size()/20)
                .build();
    }
    @Transactional
    @Override
    public ResponseEntity<?> createOrderWithDetails(OrderWithDetailsDTO dto, String phoneNumber) throws DataNotFoundException {
        User existingUser = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new DataNotFoundException(MessageKeys.DATA_NOT_FOUND));

        Order newOrder = Order.fromOrderWithDetails(dto, existingUser);
        Order savedOrder = orderRepository.save(newOrder);

        if (savedOrder != null) {
            List<OrderDetail> orderDetails = dto.getCartIds().stream().map(id -> {
                try {
                    CartProduct existingCartProduct = cartProductRepository.findById(id)
                            .orElseThrow(() -> new DataNotFoundException(MessageKeys.NON_EXISTING_CART_PRODUCT + id));

                    OrderDetail newOrderDetail = OrderDetail.builder()
                            .order(savedOrder)
                            .product(existingCartProduct.getProduct())
                            .numberOfProducts(existingCartProduct.getQuantity())
                            .price(existingCartProduct.getProduct().getPrice())
                            .totalMoney(existingCartProduct.getProduct().getPrice() * existingCartProduct.getQuantity())
                            .build();

                    return orderDetailRepository.save(newOrderDetail);
                } catch (DataNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }).collect(Collectors.toList());

            cartProductRepository.deleteAllById(dto.getCartIds());

            savedOrder.setOrderDetails(orderDetails);
            Order resavedOrder = orderRepository.save(savedOrder);

            return ResponseEntity.ok().body(MessageKeys.CREATE_ORDER_SUCCESSFULLY);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Cannot save Order");
    }

    @Override
    public ResponseEntity<?> changingStatus(Long orderId, String status) {
        try {
            Order existingOrder = orderRepository.findById(orderId)
                    .orElseThrow(() -> new DataNotFoundException(MessageKeys.DATA_NOT_FOUND));

            existingOrder.setStatus(status);
            orderRepository.saveAndFlush(existingOrder);

            return ResponseEntity.ok().body(MessageKeys.UPDATE_SUCCESSFULLY);
        } catch (DataNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


}
