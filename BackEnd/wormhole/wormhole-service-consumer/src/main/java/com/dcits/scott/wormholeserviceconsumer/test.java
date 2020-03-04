package com.dcits.scott.wormholeserviceconsumer;

import com.dcits.scott.wormholeserviceconsumer.interfacecontroller.createinterface.zk.ZkService;
import com.google.common.annotations.VisibleForTesting;
import org.I0Itec.zkclient.ZkClient;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.utils.ReferenceConfigCache;
import org.apache.dubbo.rpc.service.GenericService;
import org.junit.Test;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class test {

//    private static final Logger logger = Logger.getLogger(DubboConsumerGenericService.class);

    private static String consumeService(){
        // 普通编码配置方式
        ApplicationConfig application = new ApplicationConfig();
        application.setName("wormhole_service_consumer");

        // 连接注册中心配置
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress("zookeeper://192.168.81.128:2181");

        ReferenceConfig<GenericService> reference = new ReferenceConfig<GenericService>();
        reference.setApplication(application);
        reference.setRegistry(registry);
        reference.setVersion("2.7.3");
        reference.setInterface("com.dcits.scott.auth.authrole.RoleService");
        reference.setGeneric(true); // 声明为泛化接口

        ReferenceConfigCache cache = ReferenceConfigCache.getCache();
        GenericService genericService = cache.get(reference);

        // 基本类型以及Date,List,Map等不需要转换，直接调用
        Object resultObj = genericService.$invoke("selectList", new String[] { "java.lang.String"},
                new Object[] {"guoxi.li"});
        removeClassFeild(resultObj);

//        String result = JSON.toJSONString(resultObj);
//        logger.info("-------------->>>result:"+ result);
        return resultObj.toString();
    }

    public static void main(String[] args) {
        int invokeConut = 1; //此处设置调用次数
        String result = "";
        for (int i=0; i<invokeConut; i++){
            result = consumeService();
            System.out.println("-----invoke--------->>>result:"+ result);
        }
    }

    private static void removeClassFeild(Object object) {
        if (object instanceof Map) {
            Map feildValueMap = (Map) object;
            if (feildValueMap.containsKey("Class") || feildValueMap.containsKey("class")) {
                feildValueMap.remove("Class");
                feildValueMap.remove("class");
            }
            Set<Map.Entry> set = feildValueMap.entrySet();
            Iterator<Map.Entry> iterator = set.iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = iterator.next();
                Object value = entry.getValue();
                if (value instanceof Map) {
                    removeClassFeild(value);
                } else {
                    return;
                }
            }
        }
    }




    @Test
    public void test(){
        String zk = "192.168.81.128:2181";
        ZkService zkService = new ZkService(zk);
        ZkClient zkClient = new ZkClient(zk,5000);
        // 普通编码配置方式
        ApplicationConfig application = new ApplicationConfig();
        application.setName("dubbo-consumer");

        // 连接注册中心配置
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress("zookeeper://192.168.81.128:2181");

        ReferenceConfig<GenericService> reference = new ReferenceConfig<GenericService>();
        reference.setApplication(application);
        reference.setRegistry(registry);
        reference.setInterface("com.dcits.scott.TestInterface");
        reference.setGeneric(true); // 声明为泛化接口

        ReferenceConfigCache cache = ReferenceConfigCache.getCache();
        GenericService genericService = cache.get(reference);

        // 基本类型以及Date,List,Map等不需要转换，直接调用
        Object result = genericService.$invoke("getPermissions", new String[] { "java.lang.Long" },
                new Object[] { 1L });
        System.out.println(result);
    }

}
