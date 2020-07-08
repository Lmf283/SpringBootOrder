package com.lmf.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@EnableCaching
public class OrderApplication {
	public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
}
