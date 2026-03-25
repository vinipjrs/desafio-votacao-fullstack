package com.cooperativa.votacao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class BackJavaApplication {
    public static void main(String[] args) {
        SpringApplication.run(BackJavaApplication.class, args);
    }
}
