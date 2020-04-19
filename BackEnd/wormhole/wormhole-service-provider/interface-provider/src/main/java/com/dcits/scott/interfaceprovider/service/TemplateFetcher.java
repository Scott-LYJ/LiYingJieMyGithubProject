/*
 * MIT License
 *
 * Copyright (c) 2019 everythingbest
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.dcits.scott.interfaceprovider.service;

import com.dcits.scott.dubbo.DubboModel;
import com.dcits.scott.dubbo.LocalStore;
import com.dcits.scott.dubbo.RequestParam;
import com.dcits.scott.dubbo.RequestTemplate;
import com.dcits.scott.other.redis.RedisService;
import com.dcits.scott.util.*;

import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 用于解析请求dubbo的参数
 */
@Service
public class TemplateFetcher {

    static Logger logger = LoggerFactory.getLogger(TemplateFetcher.class);


    @Resource
    RedisService redisService;

    public RequestTemplate getTemplate(Request request) {

        String requestPath = request.getPath();

        Object object = redisService.mapGet(RedisKeys.CACHED_TEMPLATES, "cache_templates");
        Object o=null;
        try {
            o = SerializeUtils.serializeToObject((String) object);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Map<String,RequestTemplate> requestTemplateMap = (Map<String, RequestTemplate>)o;
        RequestTemplate oldTemplate = requestTemplateMap.get(requestPath) ;

        if(oldTemplate == null){

//            throw new ParamException(requestPath+":对应的服务不存在");
        }
        Object object1 = redisService.mapGet(RedisKeys.DUBBO_MODEL_MAP,"dubbo_model_map");
        Object o1=null;
        try {
            o1 = SerializeUtils.serializeToObject((String) object);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }



        Map<String,Object> bodyMap = JSON.parseObject(request.getBody(), Map.class);

        if(bodyMap == null){

//            throw new ParamException("请求参数不能为空");
        }
        //
        RequestTemplate template = (RequestTemplate)oldTemplate.clone();

        Map<String,Object> externalParams = request.getParams();

        parseExternalParams(externalParams,template);

        List<RequestParam> paramList = template.getMatcherParams();

        for(RequestParam param : paramList){
            Object paramValue=null;
            String paramType = param.getParaType();
            if ("java.util.Map".equals(paramType)){
                 paramValue = bodyMap;
            }else {
                 paramValue = bodyMap.get(param.getParaName());
            }
            template.getParamTypes().add(paramType);
            template.getParamValues().add(paramValue);
        }

        return template;
    }

    void parseExternalParams(Map<String,Object> queryParams, RequestTemplate template)  {

        if(queryParams.get("ZK") != null){

            String zk = queryParams.get("ZK").toString();

            String accessZk = CommonUtil.buildZkUrl(zk);

            template.setRegistry(accessZk);
        }
    }
}
