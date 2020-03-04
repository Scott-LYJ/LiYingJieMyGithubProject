package com.dcits.scott.wormholeserviceconsumer.interfacecontroller.testinterface;

import com.alibaba.fastjson.JSON;
import com.dcits.scott.dubbo.DubboInterfaceModel;
import com.dcits.scott.dubbo.DubboModel;
import com.dcits.scott.other.redis.RedisService;
import com.dcits.scott.support.entity.InterfaceMetaInfo;
import com.dcits.scott.support.result.WebApiRspDto;
import com.dcits.scott.util.CommonUtil;
import com.dcits.scott.util.RedisKeys;
import com.dcits.scott.util.SerializeUtils;
import com.dcits.scott.wormholeserviceconsumer.interfacecontroller.createinterface.zk.ZkServiceFactory;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/testInterface")
public class TestInterface {
    @Reference
    RedisService redisService;

    /**
     * 返回已经注册的服务名称
     * @param zk 指定的zk地址
     * @return 返回指定zk下面的所有已经注册的服务名称
     */
    @RequestMapping(value = "result/serviceNames",method = {RequestMethod.GET})
    @ResponseBody
    public WebApiRspDto getServiceName(@RequestParam(value = "zk") String zk){

        Set<Object> serviceNameSet = redisService.members("["+zk+"]");

        return WebApiRspDto.success(serviceNameSet);
    }

    /**
     * 返回所有的接口
     * @param serviceName 服务名称
     * @return
     */
    @RequestMapping(value = "result/interfaceNames",method = {RequestMethod.GET})
    @ResponseBody
    public WebApiRspDto getInterfaces(@RequestParam("zk") String zk,@RequestParam("serviceName") String serviceName){

        try {

            Map<String, String> interfaceMap = getAllClassName(zk, serviceName);

            return WebApiRspDto.success(interfaceMap);

        }catch (Exception exp){

            return WebApiRspDto.error(exp.getMessage());
        }
    }
    @RequestMapping(value = "result/interface",method = {RequestMethod.GET})
    @ResponseBody
    public WebApiRspDto getInterfaces(@RequestParam("zk") String zk,
                                      @RequestParam("serviceName") String serviceName,
                                      @RequestParam("interfaceKey") String interfaceKey){


        String modelKey = CommonUtil.getDubboModelKey("["+zk+"]", serviceName);

        Object object = redisService.mapGet(RedisKeys.DUBBO_MODEL_KEY,modelKey);
        Object o=null;
        Map<String,String> interfaceMap = new HashMap<>(10);
        try {
            o = SerializeUtils.serializeToObject((String) object);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        DubboModel dubboModel = (DubboModel)o;
//        DubboModel dubboModel = LocalStore.DUBBO_MODEL_MAP.get(modelKey);

        if(dubboModel == null){

            return WebApiRspDto.error("服务不存在,请先创建或刷新服务!");
        }

        dubboModel.setServiceName(serviceName);

        for(DubboInterfaceModel interfaceModel : dubboModel.getServiceModelList()){

            InterfaceMetaInfo metaItem = ZkServiceFactory.get(zk).allProviders.get(serviceName).get(interfaceKey);

            //根据接口名称匹配接口对应的服务
            if(interfaceModel.getKey().equals(interfaceKey)){

                //用于实时同步应用的所有ip
                interfaceModel.setServerIps(metaItem.getServerIps());

                return WebApiRspDto.success(interfaceModel);
            }
        }

        return WebApiRspDto.error("查找接口对应的服务异常");
    }




   public Map<String,String> getAllClassName(String zk, String serviceName)  {

        String modelKey = CommonUtil.getDubboModelKey("["+zk+"]",serviceName);

        Object object = redisService.mapGet(RedisKeys.DUBBO_MODEL_KEY,modelKey);
        Object o=null;
        Map<String,String> interfaceMap = new HashMap<>(10);
       try {
            o = SerializeUtils.serializeToObject((String) object);
       } catch (IOException e) {
           e.printStackTrace();
       } catch (ClassNotFoundException e) {
           e.printStackTrace();
       }
       //  DubboModel dubboModel = JSON.parseObject((String)object,DubboModel.class);
       DubboModel dubboModel = (DubboModel)o;
        for(DubboInterfaceModel serviceModel : dubboModel.getServiceModelList()){

            String className = serviceModel.getInterfaceName();
            String simpleClassName = className.substring(className.lastIndexOf(".")+1);

            interfaceMap.put(simpleClassName,serviceModel.getKey());
        }

        return interfaceMap;
    }

}
