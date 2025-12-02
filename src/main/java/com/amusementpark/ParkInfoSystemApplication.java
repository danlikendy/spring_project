package com.amusementpark;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.amusementpark")
public class ParkInfoSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParkInfoSystemApplication.class, args);
    }
}

