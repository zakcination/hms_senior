package com.hms;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.hms.entity")
@EnableJpaRepositories(basePackages = "com.hms.repository")
@ComponentScan(basePackages = { "com.hms.service", "com.hms.controller", "com.hms.config" })
@EnableJpaAuditing
@OpenAPIDefinition(info = @Info(title = "Housing Management System API", version = "1.0", description = "REST API documentation for Housing Management System"))
public class HousingManagementSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(HousingManagementSystemApplication.class, args);
    }
}