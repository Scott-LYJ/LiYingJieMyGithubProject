package com.dcits.scott.interfaceprovider.gatewayapi.serviceImpl;

import com.dcits.scott.interfaceprovider.service.GenericDubboConsumer;
import com.dcits.scott.other.dubbo.DubboProxyService;
import com.dcits.scott.support.result.WebApiRspDto;
import com.dcits.scott.util.Request;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;

@Service
public class DubboProxyServiceImpl implements DubboProxyService {

    @Resource
    GenericDubboConsumer genericDubboConsumer;
    @Override
    public WebApiRspDto send(Request request) {

        return genericDubboConsumer.send(request);
    }
}
