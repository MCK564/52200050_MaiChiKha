package com.java;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        // Initialize Spring Context
        ApplicationContext context = new ClassPathXmlApplicationContext("appConfig.xml");

        // Load Beans
        Product firstBean = (Product) context.getBean("firstBean");
        Product secondBean = (Product) context.getBean("secondBean");
        Product thirdBean = (Product) context.getBean("thirdBean");

        // Print Bean Information
        System.out.println("First Bean: " + firstBean);
        System.out.println("Second Bean: " + secondBean);
        System.out.println("Third Bean: " + thirdBean);

        // Test if first two beans are prototype and third bean is singleton
        System.out.println("First Bean is Prototype: " + (context.isPrototype("firstBean")));
        System.out.println("Second Bean is Prototype: " + (context.isPrototype("secondBean")));
        System.out.println("Third Bean is Singleton: " + (context.isSingleton("thirdBean")));
    }
}