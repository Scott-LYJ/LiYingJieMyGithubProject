package com.dcits.scott.projectprovider;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@SpringBootApplication
@MapperScan("com.dcits.scott.projectprovider")
@EnableHystrix

public class ProjectProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectProviderApplication.class, args);
    }

}
