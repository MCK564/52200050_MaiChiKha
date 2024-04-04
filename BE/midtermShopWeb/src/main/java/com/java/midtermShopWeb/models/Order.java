package com.java.midtermShopWeb.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.java.midtermShopWeb.constants.MessageKeys;
import com.java.midtermShopWeb.dtos.OrderWithDetailsDTO;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "fullname", length = 100)
    private String fullName;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "phone_number", nullable = false, length = 100)
    private String phoneNumber;

    @Column(name = "address", length = 100)
    private String address;

    @Column(name = "note", length = 100)
    private String note;

    @Column(name = "order_date")
    private LocalDate orderDate;

    @Column(name = "status")
    private String status;

    @Column(name = "total_money")
    private Float totalMoney;

    @Column(name = "shipping_method")
    private String shippingMethod;

    @Column(name = "shipping_address")
    private String shippingAddress;

    @Column(name = "shipping_date")
    private LocalDate shippingDate;

    @Column(name = "tracking_number")
    private String trackingNumber;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "active")
    private Boolean active;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<OrderDetail> orderDetails;

    public static Order fromOrderWithDetails(OrderWithDetailsDTO dto, User user){
        return Order.builder()
                .user(user)
                .active(true)
                .fullName(dto.getFullName())
                .phoneNumber(dto.getPhoneNumber())
                .email(dto.getEmail())
                .shippingAddress(dto.getShippingAddress())
                .shippingMethod(dto.getShippingMethod())
                .paymentMethod(dto.getPaymentMethod())
                .address(user.getAddress())
                .totalMoney(dto.getTotalMoney())
                .orderDate(LocalDate.now())
                .note(dto.getNote())
                .shippingDate(LocalDate.now())
                .status(MessageKeys.ORDER_STATUS_PENDING)
                .build();
    }
}

