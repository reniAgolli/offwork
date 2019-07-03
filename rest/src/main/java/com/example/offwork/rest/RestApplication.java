package com.example.offwork.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = "com.example.offwork")
@EnableJpaRepositories(basePackages = "com.example.offwork.persistence")
@EnableTransactionManagement
@EntityScan(basePackages = "com.example.offwork.domain")

public class RestApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(RestApplication.class, args);

    }

}
