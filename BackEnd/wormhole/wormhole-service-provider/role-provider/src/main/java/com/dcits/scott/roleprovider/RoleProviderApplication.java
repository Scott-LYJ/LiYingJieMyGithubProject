package com.dcits.scott.roleprovider;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.dcits.scott.roleprovider")
public class RoleProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(RoleProviderApplication.class, args);
    }

}
