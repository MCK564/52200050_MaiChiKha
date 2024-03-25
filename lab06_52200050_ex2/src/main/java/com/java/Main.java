package com.java;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        // Retrieve beans from the context
        Product firstProduct = context.getBean("firstBean", Product.class);
        Product secondProduct = context.getBean("secondBean", Product.class);
        Product thirdProduct = context.getBean("thirdBean", Product.class);

        // Print information about the beans
        System.out.println("First Product: " + firstProduct);
        System.out.println("Second Product: " + secondProduct);
        System.out.println("Third Product: " + thirdProduct);

        context.close();
    }
}