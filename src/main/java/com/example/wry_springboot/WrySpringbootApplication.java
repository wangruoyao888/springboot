package com.example.wry_springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.wry_springboot.dao")
public class WrySpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(WrySpringbootApplication.class, args);
    }
}
