package com.dcits.scott.roleprovider;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@SpringBootApplication
@MapperScan("com.dcits.scott.roleprovider")
@EnableHystrix

public class RoleProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(RoleProviderApplication.class, args);
    }

}
