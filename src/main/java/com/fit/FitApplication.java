package com.fit;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.fit.sys.mapper")
public class FitApplication {
    public static void main(String[] args) {
        SpringApplication.run(FitApplication.class, args);
    }
}
