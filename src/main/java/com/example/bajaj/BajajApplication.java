package com.example.bajaj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.WebApplicationType;

@SpringBootApplication
public class BajajApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(BajajApplication.class);
        app.setWebApplicationType(WebApplicationType.NONE); // ✅ no server
        app.run(args);
    }
}