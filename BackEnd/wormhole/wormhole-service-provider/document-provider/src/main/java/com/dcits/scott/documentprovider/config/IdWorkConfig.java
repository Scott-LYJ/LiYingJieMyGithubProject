package com.dcits.scott.documentprovider.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@EnableConfigurationProperties(IdWorkProperties.class)
@Component
public class IdWorkConfig {
    @Bean
    public IdWorker createWorker(IdWorkProperties idWorkProperties){
        return new IdWorker(idWorkProperties.getWorkerId(),idWorkProperties.getDatacenterId());
    }

}
