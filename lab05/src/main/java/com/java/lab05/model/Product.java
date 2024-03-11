package com.java.lab05.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="product")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer price;
}
