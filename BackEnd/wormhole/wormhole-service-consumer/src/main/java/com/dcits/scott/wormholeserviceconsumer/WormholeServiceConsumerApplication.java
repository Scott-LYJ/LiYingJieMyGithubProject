package com.dcits.scott.wormholeserviceconsumer;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@Import(FdfsClientConfig.class) // 导入FastDFS-Client组件

public class WormholeServiceConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WormholeServiceConsumerApplication.class, args);
    }

}
