package com.java;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AppConfig {
    @Bean
    @Scope("prototype")
    public Product firstBean() {
        Product product = new Product();
        product.setId(1);
        product.setName("First Product");
        product.setPrice(10.0);
        product.setDescription("First Product Description");
        return product;
    }

    @Bean
    @Scope("prototype")
    public Product secondBean() {
        return new Product(2, "Second Product", 20.0, "Second Product Description");
    }

    @Bean
    @Scope("singleton")
    public Product thirdBean() {
        return new Product(3, "Third Product", 30.0, "Third Product Description");
    }

}
