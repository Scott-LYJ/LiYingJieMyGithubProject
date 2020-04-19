package com.dcits.scott.interfaceprovider;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@SpringBootApplication
@MapperScan("com.dcits.scott.interfaceprovider")
@EnableHystrix
public class InterfaceProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(InterfaceProviderApplication.class, args);
    }

}
