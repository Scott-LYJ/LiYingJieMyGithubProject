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

public class EncoderClassVo implements Encoder.Text<GatewayMessageDO>{

    @Override
    public void init(EndpointConfig config) {
        // TODO Auto-generated method stub

    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }
//    //我向web端传递的是Map类型的
//    @Override
//    public String encode(Map<String, List> map) throws EncodeException {
//        ObjectMapper mapMapper= new ObjectMapper();
//        try {
//            String json="";
//            json=mapMapper.writeValueAsString(map);
//            return  json;
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            return "false";
//        }
//    }
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