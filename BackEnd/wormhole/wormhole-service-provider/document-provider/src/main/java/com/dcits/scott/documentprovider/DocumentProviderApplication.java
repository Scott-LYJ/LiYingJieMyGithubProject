package com.dcits.scott.documentprovider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@SpringBootApplication
@EnableHystrix
public class DocumentProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(DocumentProviderApplication.class, args);
    }

}
