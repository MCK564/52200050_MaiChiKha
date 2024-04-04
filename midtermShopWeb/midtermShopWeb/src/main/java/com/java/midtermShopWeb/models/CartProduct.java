package com.java.midtermShopWeb.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "carts_products")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity;

    @ManyToOne()
    @JoinColumn(name="user_id")
    private User user;
}
