package com.dcits.scott.interfaceprovider;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.dcits.scott.interfaceprovider")
public class InterfaceProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(InterfaceProviderApplication.class, args);
    }

}
