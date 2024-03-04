package com.java._52200050.ex3;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@Builder
public class ProductManager {
    private List<Product> products ;
    private long size;

    public ProductManager(List<Product> products, long size){
        this.products = products;
        this.size = products.size();
    }

    public ProductManager (){
        products= new ArrayList<>();
        products.add(new Product(1L,"milk",10));
        products.add(new Product(2L,"meat",20));
        products.add(new Product(3L,"laptop",1299));
        products.add(new Product(4L,"phone",1399));
        this.size=4;
    }
}

