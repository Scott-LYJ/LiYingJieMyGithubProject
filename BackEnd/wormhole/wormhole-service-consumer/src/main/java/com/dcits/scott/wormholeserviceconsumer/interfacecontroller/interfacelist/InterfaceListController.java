package com.dcits.scott.wormholeserviceconsumer.interfacecontroller.interfacelist;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dcits.scott.admin.pojo.AuthUser;
import com.dcits.scott.gateway.pojo.GatewayApiDO;
import com.dcits.scott.gateway.pojo.GatewayServiceRequestDO;
import com.dcits.scott.other.SolrSearchService;
import com.dcits.scott.project.gatewayapi.GatewayApiService;
import com.dcits.scott.project.gatewayservicerequest.GatewayServiceRequestService;
import com.dcits.scott.support.result.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/interfaceList")
public class InterfaceListController {
    @Reference
    GatewayApiService gatewayApiService;
    @Reference
    GatewayServiceRequestService gatewayServiceRequestService;
    @Reference
    SolrSearchService solrSearchService;

    @PostMapping("/querySysInterfaceList")
    public Result<List<GatewayApiDO>> querySysInterfaceList(@RequestBody Map<String,Object> map){
        Integer count = gatewayApiService.selectSearchCount(map);
        List<GatewayApiDO> gatewayApiDOS = gatewayApiService.selectSearchPage(map);
        return new Result<>(Result.OK,"查询成功","",gatewayApiDOS,count);
    }
    @PostMapping("/delApiByIds")
    public Result<String> delApiByIds(@RequestBody Map<String,Object> map){
        List<String> ids = new ArrayList<>();
        String ids1 = String.valueOf(map.get("ids"));
        String[] split = ids1.split(",");
        for (String id:split){
            ids.add(id);
        }
        map.put("ids",ids);
        gatewayApiService.deleteByIds(map);
        solrSearchService.deleteById(ids);
        return new Result<>(Result.OK,"删除成功","");
    }
    @PostMapping("/updateApiById")
    public Result<String> updateApiById(@RequestBody String json){
         AuthUser authUser = (AuthUser)(SecurityUtils.getSubject().getPrincipal());
            GatewayApiDO gatewayApiDO = JSON.parseObject(json, GatewayApiDO.class);
            gatewayApiDO.setUpdateBy(authUser.getName());
            //更新api表
            try {
                gatewayApiService.update(gatewayApiDO);
            } catch (Exception e) {
                e.printStackTrace();
            }
            gatewayApiDO.setPid(gatewayApiDO.getId()+"");
            solrSearchService.addBean(gatewayApiDO);
        return new Result<>(Result.OK,"更新成功","");
    }
    @PostMapping("/updateRequestById")
    public Result<String> updateRequestById(@RequestBody String json){
        List<GatewayServiceRequestDO> object = (List<GatewayServiceRequestDO>)JSONArray.parseArray(json, GatewayServiceRequestDO.class);
        for (GatewayServiceRequestDO gatewayServiceRequestDO:object
             ) {
            //更新api表
            try {
                gatewayServiceRequestService.updateByPrimaryKeySelective(gatewayServiceRequestDO);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return new Result<>(Result.OK,"","");
    }

}
