package com.dcits.scott.tool;

import org.apache.dubbo.config.AbstractReferenceConfig;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.rpc.service.GenericService;



public class TestReferenceConfig<GenericService>  extends ReferenceConfig<GenericService> {
    @Override
    public void setApplication(ApplicationConfig application) {
        this.application = application;
    }
}
