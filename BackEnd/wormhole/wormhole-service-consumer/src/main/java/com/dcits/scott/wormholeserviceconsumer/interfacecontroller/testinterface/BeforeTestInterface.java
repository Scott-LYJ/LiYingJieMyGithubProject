package com.dcits.scott.wormholeserviceconsumer.interfacecontroller.testinterface;

import com.dcits.scott.dubbo.*;
import com.dcits.scott.gateway.pojo.GatewayServiceRequestDO;
import com.dcits.scott.other.redis.RedisService;
import com.dcits.scott.project.gatewayapi.GatewayApiService;
import com.dcits.scott.project.gatewayservicerequest.GatewayServiceRequestService;
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
import java.util.*;

@RestController
@RequestMapping("/beforeTestInterface")
public class BeforeTestInterface {
    private Boolean bool = true;
    @Reference
    RedisService redisService;
    @Reference
    GatewayServiceRequestService gatewayServiceRequestService;
    @Reference
    GatewayApiService gatewayApiService;
    @PostMapping("/createDubboModel")
    public String createDubboModel(@RequestBody Map<String,Object> map) throws IOException {
        String zk="";
        Long apiId=null;
        if(map.get("zookeeper")!=null) {
            zk = String.valueOf(map.get("zookeeper"));
             apiId = Long.parseLong(String.valueOf(map.get("id")));
        }
        if (map.get("zk")!=null){
            zk = String.valueOf(map.get("zk"));
             apiId = Long.parseLong(String.valueOf(map.get("id")));
        }


        //
       //修改测试时间
        gatewayApiService.updateTestTime(apiId);
        //
        String g = String.valueOf(map.get("groupId"));
        String v = String.valueOf(map.get("serviceVersion"));
        String serviceName = String.valueOf(map.get("serviceName"));
        String interfaceName = String.valueOf(map.get("interfaceName"));
        String serviceMethod = String.valueOf(map.get("serviceMethod"));
        String modelKey = CommonUtil.getDubboModelKey(zk, serviceName);
        Object object1 = redisService.mapGet(RedisKeys.CACHED_TEMPLATES, "cache_templates");
        if (object1!=null){
            Object o=null;
            String requestPath = "/"+serviceName+"/"+interfaceName+"/"+serviceMethod;
            try {
                o = SerializeUtils.serializeToObject((String) object1);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Map<String,RequestTemplate> requestTemplateMap = (Map<String, RequestTemplate>) o;
            RequestTemplate oldTemplate = requestTemplateMap.get(requestPath) ;

            if(oldTemplate != null){
                return "";
            }
        }
        String str = (String) redisService.mapGet(RedisKeys.DUBBO_MODEL_KEY, modelKey);
        if (str==null){

        }
        List<GatewayServiceRequestDO> gatewayServiceRequestDOList = gatewayServiceRequestService.selectByApiId(apiId);

        try {
            Object object = SerializeUtils.serializeToObject(str);
            DubboModel dubboModel = (DubboModel) object;
            List<DubboInterfaceModel> serviceModelList = dubboModel.getServiceModelList();
            List<DubboParamModel> dubboParamModels = new ArrayList<>();

            for (GatewayServiceRequestDO gatewayServiceRequestDO : gatewayServiceRequestDOList) {
                DubboParamModel dubboParamModel = new DubboParamModel();
                dubboParamModel.setName(gatewayServiceRequestDO.getName());
                dubboParamModel.setType(gatewayServiceRequestDO.getTypeName());
                dubboParamModel.setExample(gatewayServiceRequestDO.getExample());
                dubboParamModel.setDescription(gatewayServiceRequestDO.getDescription());
                dubboParamModels.add(dubboParamModel);
            }

            for (DubboInterfaceModel dubboInterfaceModel : serviceModelList) {
                if (dubboInterfaceModel.getKey().equals(interfaceName)) {
                    List<DubboMethodModel> methodModels = new ArrayList<>(dubboInterfaceModel.getMethodNames().size());

                    Set<String> set = dubboInterfaceModel.getMethodNames();
                    Iterator<String> it = set.iterator();
                    while (it.hasNext()) {
                        String str1 = it.next();
                        if (str1.equals(serviceMethod)){
                            DubboMethodModel dubboMethodModel = new DubboMethodModel();
                            dubboMethodModel.setName(serviceMethod);
                            dubboMethodModel.setParams(dubboParamModels);
                            methodModels.add(dubboMethodModel);
                        }
                    }
                    dubboInterfaceModel.setMethods(methodModels);
                }
            }
            dubboModel.setServiceModelList(serviceModelList);
            //序列化
            String modelString = SerializeUtils.serialize(dubboModel);
            //更新redis
            redisService.mapPut(RedisKeys.DUBBO_MODEL_KEY,modelKey,modelString);
            //构建requesttemplete
            buildRequestDubboTemplate(dubboModel,interfaceName,serviceMethod);
            System.out.println(dubboModel.getServiceName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void buildRequestDubboTemplate(DubboModel model,String interfaceName,String serviceMethod) throws IOException {

      buildTemplateByDubboModel(model,interfaceName,serviceMethod);

      addTemplateRuntimeInfo(model);

    }

    private void persistent() throws IOException {

        redisService.mapPut(RedisKeys.CACHED_TEMPLATES,"cache_templates", SerializeUtils.serialize(LocalStore.CACHED_TEMPLATES));
        redisService.mapPut(RedisKeys.DUBBO_MODEL_MAP,"dubbo_model_map", SerializeUtils.serialize(LocalStore.DUBBO_MODEL_MAP));
        Map<String, RequestTemplate> cachedTemplates = LocalStore.CACHED_TEMPLATES;
        Map<String, DubboModel> dubboModelMap = LocalStore.DUBBO_MODEL_MAP;
    }


    public void buildTemplateByDubboModel(DubboModel model,String interfaceName,String serviceMethod){

        for(DubboInterfaceModel interfaceModel : model.getServiceModelList()){
                if (interfaceName.equals(interfaceModel.getKey())) {
                    for (String methodNam : interfaceModel.getMethodNames()) {
                            if (serviceMethod.equals(methodNam)){
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
                    }
                    }
                }
        }
    }
    //
    public void addTemplateRuntimeInfo(DubboModel dubboModel){

        //加载这个服务的所有方法
        for(DubboInterfaceModel interfaceModel : dubboModel.getServiceModelList()) {
            if (interfaceModel.getMethods() != null) {
                for (DubboMethodModel methodModel : interfaceModel.getMethods()) {

                    String path = requestPath(dubboModel, interfaceModel, methodModel.getName());

                    RequestTemplate template = LocalStore.get(path);

                    if (template != null) {

                        addMethodWithParameters(methodModel, template);

                        template.setHasRunTimeInfo(true);
                        try {
                            persistent();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        }
    }
    //
    void addMethodWithParameters(DubboMethodModel methodModel, RequestTemplate template){

        template.setMethodName(methodModel.getName());

        //设置参数类型及名称
        if (methodModel.getParams().size() >= 1) {

            int paramIndex = 0;

            for (DubboParamModel paramModel : methodModel.getParams()) {

                RequestParam requestParam = new RequestParam();

                requestParam.setExample(paramModel.getExample());
                requestParam.setDescription(paramModel.getDescription());

                requestParam.setParaName(paramModel.getName());
                requestParam.setParaType(paramModel.getType());


                paramIndex++;


                template.getMatcherParams().add(requestParam);
            }
        }


    }
    //
    String requestPath(DubboModel dubboModel, DubboInterfaceModel interfaceModel, String methodName){

        String realPath =
                dubboModel.getServiceName() + "/" + interfaceModel.getGroup() + "/"
                        + interfaceModel.getInterfaceName().replace(".", "/");

        if (!interfaceModel.getVersion().isEmpty()) {
            realPath += "/" + interfaceModel.getVersion().replace(".", "/");
        }

        realPath = "/" + realPath + "/" + methodName;

        return realPath;
    }
}
