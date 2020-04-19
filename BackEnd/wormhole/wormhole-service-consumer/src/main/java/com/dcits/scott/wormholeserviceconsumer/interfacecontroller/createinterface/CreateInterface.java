package com.dcits.scott.wormholeserviceconsumer.interfacecontroller.createinterface;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dcits.scott.admin.pojo.AuthUser;
import com.dcits.scott.dubbo.DubboInterfaceModel;
import com.dcits.scott.dubbo.DubboModel;
import com.dcits.scott.dubbo.LocalStore;
import com.dcits.scott.dubbo.RequestTemplate;
import com.dcits.scott.gateway.pojo.GatewayApiDO;
import com.dcits.scott.gateway.pojo.GatewayApiGroupDO;
import com.dcits.scott.gateway.pojo.GatewayServiceRequestDO;
import com.dcits.scott.other.SolrSearchService;
import com.dcits.scott.other.redis.RedisService;
import com.dcits.scott.project.gatewayapi.GatewayApiService;
import com.dcits.scott.project.gatewayapigroup.GatewayApiGroupService;
import com.dcits.scott.project.gatewayservicerequest.GatewayServiceRequestService;
import com.dcits.scott.support.entity.InterfaceMetaInfo;
import com.dcits.scott.support.result.Result;
import com.dcits.scott.support.result.WebApiRspDto;
import com.dcits.scott.util.CommonUtil;
import com.dcits.scott.util.RedisKeys;
import com.dcits.scott.util.SerializeUtils;
import com.dcits.scott.util.TypeName;
import com.dcits.scott.wormholeserviceconsumer.config.zk.ZkServiceFactory;
import com.google.common.collect.Maps;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.shiro.SecurityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.bind.annotation.*;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

@RestController
@RequestMapping("/createrInterface")
public class CreateInterface {
    @Reference
    GatewayApiService gatewayApiService;

    @Reference
    RedisService redisService;

    @Reference
    GatewayServiceRequestService gatewayServiceRequestService;

    @Reference
    GatewayApiGroupService gatewayApiGroupService;

    @Reference
    SolrSearchService solrSearchService;
    //插入数据
    @PostMapping("/insertInterface")
    public Result<GatewayApiDO> insertInterface(@RequestBody Map<String,Object> map){
        try{
//            List<String> list = (List<String>) map.get("zookeeper");
//            StringBuilder sb = new StringBuilder();
//            for (int i = 0; i < list.size(); i++) {
//                sb.append(list.get(i)).append(';');
//
//            }
//            map.put("zookeeper",sb.toString().substring(0, sb.toString().length() - 1));
            AuthUser authUser = (AuthUser) SecurityUtils.getSubject().getPrincipal();
            String createBy = authUser.getName();
            String updateBy = createBy;
            map.put("createBy",createBy);
            map.put("updateBy",updateBy);
            map.put("authVersion",Integer.parseInt(String.valueOf(map.get("authVersion"))==""?"-1":String.valueOf(map.get("authVersion"))));
            map.put("isLogin",Integer.parseInt(String.valueOf(map.get("isLogin"))));
            map.put("status",Integer.parseInt(String.valueOf(map.get("status"))));
            map.put("id",-1L);
            map.put("groupId",Long.parseLong(String.valueOf(map.get("groupId"))));
            //1.获取bean信息
            GatewayApiDO gatewayApiDO = new GatewayApiDO();
            BeanInfo beanInfo = Introspector.getBeanInfo(gatewayApiDO.getClass());
            PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor prop: properties) {
                String key = prop.getName();
                if(map.containsKey(key) && (map.get(key) != null||String.valueOf(map.get(key)) != "")){
                    Object value = map.get(key);
                    Method setMethod = prop.getWriteMethod();
                    setMethod.invoke(gatewayApiDO,value);
                }
            }
            GatewayApiDO gatewayApiDO1 = gatewayApiService.insertByDO(gatewayApiDO);
            GatewayApiGroupDO gatewayApiGroupDO = gatewayApiGroupService.selectId(gatewayApiDO1.getGroupId());
            gatewayApiDO.setGroupName(gatewayApiGroupDO.getName());
            gatewayApiDO.setId(gatewayApiDO1.getId());
            Long id = gatewayApiDO1.getId();
            ObjectMapper objectMapper = new ObjectMapper();
            List<GatewayServiceRequestDO> gatewayServiceRequestDOList = new ArrayList<>();
            List<Object> tableData = (List) map.get("tableData");
             for (int i=0;i<tableData.size();i++){
                 Object object= tableData.get(i);
                 String json = objectMapper.writeValueAsString(object);
                 GatewayServiceRequestDO gatewayServiceRequestDO = objectMapper.readValue(json, GatewayServiceRequestDO.class);
                 gatewayServiceRequestDO.setApiId(id);
                 gatewayServiceRequestDO.setParamsIndex(i+1);
                 gatewayServiceRequestDO.setVersion(i+1);
                 gatewayServiceRequestDO.setIsRequired(gatewayApiDO1.getStatus());

                 switch (gatewayServiceRequestDO.getTypeName()){
                     case TypeName.Float:gatewayServiceRequestDO.setType(GatewayServiceRequestDO.FLOAT);break;
                     case TypeName.Integer:gatewayServiceRequestDO.setType(GatewayServiceRequestDO.INT);break;
                     case TypeName.Long:gatewayServiceRequestDO.setType(GatewayServiceRequestDO.LONG);break;
                     case TypeName.Map:gatewayServiceRequestDO.setType(GatewayServiceRequestDO.OTHERS);break;
                     case TypeName.String:gatewayServiceRequestDO.setType(GatewayServiceRequestDO.STRING);break;
                 }

                 gatewayServiceRequestDOList.add(gatewayServiceRequestDO);
            }
            gatewayServiceRequestService.batchInsert(gatewayServiceRequestDOList);
             gatewayApiDO.setCdt(new Date());
             gatewayApiDO.setPid(gatewayApiDO1.getId()+"");
            solrSearchService.addBean(gatewayApiDO);
            return  new Result<>(Result.OK,"插入成功",gatewayApiDO1);

        }catch(Exception e){
            e.printStackTrace();
        }
        return new Result<>(Result.ERROR,"插入失败",null);
    }

    /**
     *
     * @return zk
     */
    @RequestMapping(value = "result/all-zk", method = RequestMethod.GET)
    @ResponseBody
    public WebApiRspDto allZk() {

        Set<Object> zkAddrs = redisService.members("dubbo_zk_address");
        return WebApiRspDto.success(zkAddrs);
    }


    /**
     * 返回已经注册的服务名称
     * @param zk 指定的zk地址
     * @return 返回指定zk下面的所有已经注册的服务名称
     */
    @RequestMapping(value = "result/serviceNames",method = {RequestMethod.GET})
    @ResponseBody
    public WebApiRspDto getServiceName(@RequestParam(value = "zk") String zk){

        Set<Object> serviceNameSet = redisService.members(zk);

        return WebApiRspDto.success(serviceNameSet);
    }


    /**
     * 返回所有的接口
     * @param serviceName 服务名称
     * @return interfaceMap 接口名称
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

    /**
     *
     * @param zk zk地址
     * @param serviceName
     * @param interfaceKey
     * @return DubboInterfaceModel
     */
    @RequestMapping(value = "result/interface",method = {RequestMethod.GET})
    @ResponseBody
    public WebApiRspDto getInterfaces(@RequestParam("zk") String zk,
                                      @RequestParam("serviceName") String serviceName,
                                      @RequestParam("interfaceKey") String interfaceKey){


        String modelKey = CommonUtil.getDubboModelKey(zk, serviceName);

        Object object = redisService.mapGet(RedisKeys.DUBBO_MODEL_KEY,modelKey);
        Object o=null;
        try {
            o = SerializeUtils.serializeToObject((String) object);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        DubboModel dubboModel = (DubboModel)o;
        if(dubboModel == null){

            return WebApiRspDto.error("服务不存在,请先创建或刷新服务!");
        }

        dubboModel.setServiceName(serviceName);

        for(DubboInterfaceModel interfaceModel : dubboModel.getServiceModelList()){

            InterfaceMetaInfo metaItem = com.dcits.scott.wormholeserviceconsumer.interfacecontroller.createinterface.zk.ZkServiceFactory.get(zk).allProviders.get(serviceName).get(interfaceKey);

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

        String modelKey = CommonUtil.getDubboModelKey(zk,serviceName);

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
        for(DubboInterfaceModel serviceModel : dubboModel.getServiceModelList()){

            String className = serviceModel.getInterfaceName();
            String simpleClassName = className.substring(className.lastIndexOf(".")+1);

            interfaceMap.put(simpleClassName,serviceModel.getKey());
        }

        return interfaceMap;
    }
    //
    /**
     * 根据服务名称,接口key,方法路径,获取参数模板
     * @param serviceName
     * @param interfaceKey
     * @param methodPath
     * @return
     */
    @RequestMapping(value = "/result/methodParams",method = {RequestMethod.GET})
    @ResponseBody
    public WebApiRspDto getResultServiceMethod(@RequestParam("zk") String zk,
                                               @RequestParam("serviceName") String serviceName,
                                               @RequestParam("interfaceKey") String interfaceKey,
                                               @RequestParam("methodPath") String methodPath){

        String modelKey = CommonUtil.getDubboModelKey(zk, serviceName);
        String path = "/"+serviceName+"/"+interfaceKey+"/"+methodPath;

        String str = (String)redisService.mapGet(RedisKeys.CACHED_TEMPLATES, "cache_templates");
        RequestTemplate requestTemplate = null;
        try {
            Object object = SerializeUtils.serializeToObject(str);
            Map<String, RequestTemplate> map = (Map<String, RequestTemplate>) object;
             requestTemplate = map.get(String.valueOf(path));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //构建methodExample
        List<com.dcits.scott.dubbo.RequestParam> matcherParams = requestTemplate.getMatcherParams();
        StringBuffer example = new StringBuffer();
        if (matcherParams.size()==1){
            requestTemplate.setMethodExample(matcherParams.get(0).getExample()+"\n"+"//"+matcherParams.get(0).getDescription());
        }else {
            example.append("{");
            for (int i=0;i<matcherParams.size();i++){
                if (i==matcherParams.size()-1){
                    example.append(matcherParams.get(i).getExample());
                }else {
                    example.append(matcherParams.get(i).getExample()).append(",");
                }
            }
            example.append("}");
            requestTemplate.setMethodExample(example.toString());
        }
        return WebApiRspDto.success(requestTemplate);
    }

}
