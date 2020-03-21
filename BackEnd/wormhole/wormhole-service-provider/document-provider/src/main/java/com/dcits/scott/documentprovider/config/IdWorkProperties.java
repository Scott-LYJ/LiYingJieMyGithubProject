package com.dcits.scott.documentprovider.config;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "sc.worker")
public class IdWorkProperties {
    private Integer workerId;
    private Integer datacenterId;

    public Integer getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Integer workerId) {
        this.workerId = workerId;
    }

    public Integer getDatacenterId() {
        return datacenterId;
    }

    public void setDatacenterId(Integer datacenterId) {
        this.datacenterId = datacenterId;
    }
}
