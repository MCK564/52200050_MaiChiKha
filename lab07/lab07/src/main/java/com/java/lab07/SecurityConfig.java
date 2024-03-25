package com.java.lab07;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {
    @Bean
    public int print(){
        System.out.println("--------------------------o0o----------------------------");
        System.out.println("|               Môn học công nghệ java                   |");
        System.out.println("--------------------------o0o----------------------------");
        return 0;
    }
}
