package com.dcits.scott.projectprovider;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.dcits.scott.projectprovider")

public class ProjectProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectProviderApplication.class, args);
    }

}
