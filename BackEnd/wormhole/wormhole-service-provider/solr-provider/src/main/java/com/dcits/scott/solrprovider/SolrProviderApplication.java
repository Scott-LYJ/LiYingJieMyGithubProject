package com.dcits.scott.solrprovider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@SpringBootApplication
@EnableHystrix

public class SolrProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(SolrProviderApplication.class, args);
    }

}
