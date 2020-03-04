package com.dcits.scott.wormholeserviceconsumer.interfacecontroller.testinterface;

import com.dcits.scott.dubbo.*;
import com.dcits.scott.other.redis.RedisService;
import com.dcits.scott.support.entity.InterfaceMetaInfo;
import com.dcits.scott.util.CommonUtil;
import com.dcits.scott.util.RedisKeys;
import com.dcits.scott.util.SerializeUtils;
import com.dcits.scott.wormholeserviceconsumer.interfacecontroller.createinterface.zk.ZkServiceFactory;
import com.dcits.scott.wormholeserviceconsumer.interfacecontroller.testinterface.function.DubboAppCreator;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

@RestController
@RequestMapping("/beforeTestInterface")
public class BeforeTestInterface {
    private Boolean bool = true;
    @Reference
    RedisService redisService;
    @PostMapping("/createDubboModel")
    public String createDubboModel(@RequestBody Map<String,Object> map) throws IOException {
        String zk = String.valueOf(map.get("zookeeper"));
        String g = String.valueOf(map.get("groupId"));
        String v = String.valueOf(map.get("serviceVersion"));
        String a = String.valueOf(map.get("createBy"));
        Map<String, Map<String, InterfaceMetaInfo>> allProviders = ZkServiceFactory.get(zk).allProviders;
        Iterator<Map.Entry<String, Map<String, InterfaceMetaInfo>>> iterator  = allProviders.entrySet().iterator();
        while (iterator.hasNext()) {

            Map.Entry<String, Map<String, InterfaceMetaInfo>> entry = iterator.next();
            String serviceName = entry.getKey();
            System.out.println(entry.getKey()+":"+entry.getValue());
            Map<String, InterfaceMetaInfo> interfaceMetaInfoMap = allProviders.get(entry.getKey());
            DubboModel dubboModel = DubboAppCreator.create(zk,serviceName,g,a,v,interfaceMetaInfoMap);

            buildRequestDubboTemplate(dubboModel);

        }



        return "";
    }

    private void buildRequestDubboTemplate(DubboModel model) throws IOException {

       // loadJarClassService.load(model);

        persistent(model);


      buildTemplateByDubboModel(model);

      //  templateBuilder.addTemplateRuntimeInfo(model);
    }

    private void persistent(DubboModel dubboModel) throws IOException {

        String modelString = SerializeUtils.serialize(dubboModel);
        String localStore = SerializeUtils.serialize(LocalStore.class);
        String modelKey = CommonUtil.getDubboModelKey(dubboModel.getZkAddress(), dubboModel.getServiceName());

        redisService.mapPut(RedisKeys.DUBBO_MODEL_KEY, modelKey, modelString);
        redisService.setAdd(dubboModel.getZkAddress(), dubboModel.getServiceName());
        redisService.mapPut(RedisKeys.DUBBO_LOCALSTORE,"dubbo_localStore",localStore);


        redisService.mapPut(RedisKeys.CACHED_TEMPLATES,"cache_templates", SerializeUtils.serialize(LocalStore.CACHED_TEMPLATES));
        redisService.mapPut(RedisKeys.DUBBO_MODEL_MAP,"dubbo_model_map", SerializeUtils.serialize(LocalStore.DUBBO_MODEL_MAP));
    }


    public void buildTemplateByDubboModel(DubboModel model){

        for(DubboInterfaceModel interfaceModel : model.getServiceModelList()){

            for(String methodNam : interfaceModel.getMethodNames()){

                String methodName = methodNam;

                //一个template对应一个唯一的方法访问
                RequestTemplate template = new RequestTemplate();

                template.setInterfaceName(interfaceModel.getInterfaceName());

                template.setDubboUrl("dubbo" + "://" + "ip");

                template.setRetries(interfaceModel.getRetries());

                String zkRegistry = CommonUtil.buildZkUrl(model.getZkAddress());

                template.setRegistry(zkRegistry);

                template.setServiceName(model.getServiceName());

                template.setVersion(interfaceModel.getVersion());

                template.setGroup(interfaceModel.getGroup());

                String requestPath = model.getServiceName() + "/" + interfaceModel.getGroup() + "/"
                        + interfaceModel.getInterfaceName().replace(".", "/");

                if (!interfaceModel.getVersion().isEmpty()) {

                    requestPath += "/" + interfaceModel.getVersion().replace(".", "/");
                }

                requestPath = "/" + requestPath + "/" + methodName;

                LocalStore.put(requestPath, template, model);
                System.out.println("");
            }
        }
    }
}
