package com.java;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.java")
@RequiredArgsConstructor
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        MyService myService = context.getBean(MyService.class);
        myService.printProperty();
        context.close();
    }
}
