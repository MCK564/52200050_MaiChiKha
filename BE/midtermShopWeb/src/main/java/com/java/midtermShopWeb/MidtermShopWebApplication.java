package com.java.midtermShopWeb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EntityScan(basePackages = {"com.java.midtermShopWeb.models"})
public class MidtermShopWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(MidtermShopWebApplication.class, args);
	}

}
