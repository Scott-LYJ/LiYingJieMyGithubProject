package com.dcits.scott.wormholeserviceconsumer;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Import;

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class, MongoAutoConfiguration.class})
@Import(FdfsClientConfig.class) // 导入FastDFS-Client组件
@EnableHystrix
public class WormholeServiceConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WormholeServiceConsumerApplication.class, args);
    }

}
