package com.dcits.scott.wormholeserviceconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class WormholeServiceConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WormholeServiceConsumerApplication.class, args);
    }

}
