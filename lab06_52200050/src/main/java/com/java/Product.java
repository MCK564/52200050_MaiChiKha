package com.java;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    private int id;
    private String name;
    private double price;
    private String description;

}
