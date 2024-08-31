package com.example.oneworkTest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.oneworkTest.station.repository")
public class OneworkTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(OneworkTestApplication.class, args);
    }

}
