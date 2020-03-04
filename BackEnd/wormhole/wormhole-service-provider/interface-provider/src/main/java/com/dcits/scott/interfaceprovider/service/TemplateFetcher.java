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
 * @author everythingbest
 */
@Service
public class TemplateFetcher {

    static Logger logger = LoggerFactory.getLogger(TemplateFetcher.class);
//
//    @Resource
//    LoadRuntimeInfo loadJarClassService;
//
//    @Resource
//    TemplateBuilder templateBuilder;

    @Resource
    RedisService redisService;

    public RequestTemplate getTemplate(Request request) {

        String requestPath = request.getPath();

//        RequestTemplate oldTemplate = LocalStore.get(requestPath);
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


//        Map<String,DubboModel> dubboModelMap =(Map<String,DubboModel>) o1;
//        DubboModel dubboModel = dubboModelMap.get(requestPath);

        //服务重启的时候需要重新构建运行时信息
//        if(!dubboModel.getLoadedToClassLoader()){
//
//            loadJarClassService.load(dubboModel);
//        }
//
//        if(!oldTemplate.isHasRunTimeInfo()){
//
//            templateBuilder.addTemplateRuntimeInfo(dubboModel);
//        }

        Map<String,Object> bodyMap = JSON.parseObject(request.getBody(), Map.class);

        if(bodyMap == null){

//            throw new ParamException("请求参数不能为空");
        }
        //
        RequestTemplate template = (RequestTemplate)oldTemplate.clone();

        Map<String,Object> externalParams = request.getParams();

        parseExternalParams(externalParams,template);

//        //方法的一级参数名称
//        List<RequestParam> paramList = template.getMatcherParams();
//
//        //遍历模板的参数名称
//        for(RequestParam param : paramList){
//
//            String paramName = param.getParaName();
//
//            Class<?> targetType = param.getTargetParaType();
//
//            boolean nullParam = false;
//
//            Object paramValue = null;
//
//            Object value = bodyMap.get(paramName);
//
//            if(value == null){
//
//                //传入null的参数
//                nullParam = true;
//
//            }else{
//
//                ClassLoader old = Thread.currentThread().getContextClassLoader();
//                Thread.currentThread().setContextClassLoader(targetType.getClassLoader());
//
//                try {
//                    paramValue = JSON.mapper.convertValue(value, targetType);
//                }catch (Exception exp){
//                    logger.error("参数反序列化失败:"+exp.getMessage());
//                }
//                Thread.currentThread().setContextClassLoader(old);
//            }
//
//            if(!nullParam && paramValue == null){
//
////                throw new ParamException("参数匹配错误,参数名称:"+paramName+",请检查类型,参数类型:"+targetType.getName());
//            }
//
//            template.getParamTypes().add(targetType.getName());
//
//            template.getParamValues().add(paramValue);
//        }
        template.getParamTypes().add("java.lang.Integer");
        template.getParamValues().add(20);
        return template;
    }

    void parseExternalParams(Map<String,Object> queryParams, RequestTemplate template)  {

        if(queryParams.get("dubboIp") != null){

            String dubboIp = queryParams.get("dubboIp").toString();

            String realIp = dubboIp.split(":")[0];

//            boolean ipAddress = IpUtil.isIp(realIp);
//
//            if(!ipAddress){
//
//                throw new ParamException(dubboIp+"不是合法的IP地址");
//            }

            template.setDubboUrl(template.getDubboUrl().replace("ip",dubboIp));

            template.setUseDubbo(true);
        }

        if(queryParams.get("ZK") != null){

            String zk = queryParams.get("ZK").toString();

            String accessZk = CommonUtil.buildZkUrl(zk);

            template.setRegistry(accessZk);
        }
    }
}
