package com.dcits.scott.wormholeserviceconsumer.interfacecontroller.createinterface;

import com.dcits.scott.dubbo.DubboModel;
import com.dcits.scott.dubbo.LocalStore;
import com.dcits.scott.other.redis.RedisService;
import com.dcits.scott.support.entity.InterfaceMetaInfo;
import com.dcits.scott.support.result.Result;
import com.dcits.scott.support.result.WebApiRspDto;
import com.dcits.scott.util.CommonUtil;
import com.dcits.scott.util.RedisKeys;
import com.dcits.scott.util.SerializeUtils;
import com.dcits.scott.wormholeserviceconsumer.interfacecontroller.createinterface.zk.ZkServiceFactory;
import com.dcits.scott.wormholeserviceconsumer.interfacecontroller.testinterface.function.DubboAppCreator;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/zkConfig")
public class ZkConfigController {

    @Reference
    RedisService redisService;


    @RequestMapping(value = "configs", method = RequestMethod.GET)
    @ResponseBody
    public WebApiRspDto configs(){

       // Set<Object> sets = redisService.members(RedisKeys.ZK_REDIS_KEY);
       // Set<Object> sets  = Collections.singleton(redisService.get(RedisKeys.ZK_REDIS_KEY));
        Set<Object> sets = redisService.members(RedisKeys.ZK_REDIS_KEY);
        return WebApiRspDto.success(sets);
    }



    private final static String PASSWORD = "123456";
   @GetMapping("/config")
    public WebApiRspDto addZk(@RequestParam(name = "zookeeper") String zk,
                              @RequestParam(name = "pass") String password){
       if(!password.equals(PASSWORD)){

           return WebApiRspDto.error("密码错误");
       }

       if(zk.isEmpty()){

           return WebApiRspDto.error("zk不能为空");
       }

       if(ZkServiceFactory.ZK_SET.contains(zk)){

           return WebApiRspDto.error("zk地址已经存在");
       }
       ZkServiceFactory.ZK_SET.add(zk);

       try {
           ZkServiceFactory.get(zk);
       }catch (Exception exp){
           return WebApiRspDto.error("zk地址连接失败:"+exp.getMessage());
       }

       redisService.setAdd(RedisKeys.ZK_REDIS_KEY,zk);

       return WebApiRspDto.success("保存成功");

   }
   //
   @RequestMapping(value = "zk/del", method = RequestMethod.GET)
   @ResponseBody
   public WebApiRspDto del(@RequestParam(name = "zk") String zk,
                           @RequestParam(name = "password") String password){

       if(password.equals(PASSWORD)){

           if(zk.isEmpty()){

               return WebApiRspDto.error("zk不能为空");
           }

           if(!ZkServiceFactory.ZK_SET.contains(zk)){

               return WebApiRspDto.error("zk地址不存在");
           }

           ZkServiceFactory.ZK_SET.remove(zk);

           ZkServiceFactory.ZKSERVICE_MAP.remove(zk);

           redisService.setRemove(RedisKeys.ZK_REDIS_KEY,zk);

           return WebApiRspDto.success("删除成功");

       }else {

           return WebApiRspDto.error("密码错误");
       }
   }

   //
   @RequestMapping(value = "env", method = RequestMethod.GET)
   @ResponseBody
   public WebApiRspDto env(){

       return WebApiRspDto.success("LOCAL");
   }
    @RequestMapping(value = "zk/del", method = RequestMethod.POST)
    @ResponseBody
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

        persistent(model);

    }


    private void persistent(DubboModel dubboModel) throws IOException {

        String modelString = SerializeUtils.serialize(dubboModel);
        String modelKey = CommonUtil.getDubboModelKey(dubboModel.getZkAddress(), dubboModel.getServiceName());

        redisService.mapPut(RedisKeys.DUBBO_MODEL_KEY, modelKey, modelString);
        redisService.setAdd(dubboModel.getZkAddress(), dubboModel.getServiceName());
    }

}
