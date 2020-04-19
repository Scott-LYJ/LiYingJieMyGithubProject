package com.dcits.scott.wormholeserviceconsumer.config;

import com.dcits.scott.gateway.pojo.GatewayMessageDO;
import com.dcits.scott.util.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 使用webSocket广播一个实体类
 */
public class EncoderClassVo implements Encoder.Text<GatewayMessageDO>{

    @Override
    public void init(EndpointConfig config) {
        // TODO Auto-generated method stub

    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }
    //如果你传递的是一个类，则使用如下写法
    @Override
    public String encode(GatewayMessageDO gatewayMessageDO) throws EncodeException {
        try {
            return JSON.objectToString(gatewayMessageDO);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

}