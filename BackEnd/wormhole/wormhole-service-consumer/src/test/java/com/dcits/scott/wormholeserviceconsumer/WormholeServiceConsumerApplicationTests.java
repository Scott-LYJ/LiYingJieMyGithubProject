package com.dcits.scott.wormholeserviceconsumer;

import org.apache.solr.client.solrj.SolrClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WormholeServiceConsumerApplicationTests {
    @Autowired
    SolrClient solrClient;
    @Test
    void contextLoads() {
        System.out.println("对象是否存在"+solrClient);
    }

}
