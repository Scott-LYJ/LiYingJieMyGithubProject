package com.dcits.scott.wormholeserviceconsumer.projectcontroller.projectapicontroller;

import com.dcits.scott.gateway.pojo.GatewayApiDO;
import com.dcits.scott.gateway.pojo.GatewayApiGroupDO;
import com.dcits.scott.gateway.pojo.GatewayProjectGroupDO;
import com.dcits.scott.project.gatewayapi.GatewayApiService;
import com.dcits.scott.project.gatewayapigroup.GatewayApiGroupService;
import com.dcits.scott.project.gatewayprojectgroup.GatewayProjectGroupService;
import com.dcits.scott.support.result.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/projectApi")
public class ProjectApiController {

    @Reference
    GatewayApiService gatewayApiService;
    @Reference
    GatewayProjectGroupService gatewayProjectGroupService;

    @PostMapping("/queryProjectAndApi")
    public Result<List<GatewayApiDO>> queryProjectAndApi(@RequestBody Map<String,Object> map) throws Exception {
        List<GatewayApiDO> gatewayApiDOS = gatewayApiService.selectSearchPage(map);
        Integer projectId = Integer.parseInt(String.valueOf(map.get("projectId")));
        List<GatewayProjectGroupDO> gatewayProjectGroupDOList=gatewayProjectGroupService.selectByProjectId(projectId);
        Map<String,Integer> map1 = new HashMap<>();
        for (GatewayProjectGroupDO gatewayProjectGroupDO: gatewayProjectGroupDOList
                ) {
            Integer apiId = gatewayProjectGroupDO.getApiId();
            map1.put(apiId+"",apiId);
        }
        for (GatewayApiDO gatewayApiDO:gatewayApiDOS
                ) {
            if (map1.containsKey(gatewayApiDO.getId()+"")){
                gatewayApiDO.setAuthorized(true);
            }else gatewayApiDO.setAuthorized(false);
        }
        return new Result<>(Result.OK,"查询成功",gatewayApiDOS);
    }
    @PostMapping("/insertAuthorized")
    public Result<String> insertAuthorized(@RequestBody Map<String,Object> map){
        gatewayProjectGroupService.insertByProApi(map);

        return new Result<>(Result.OK,"插入成功");
    }
    @PostMapping("/deleteAuthorized")
    public Result<String> deleteAuthorized(@RequestBody Map<String,Object> map){
        gatewayProjectGroupService.deleteByProApi(map);
        return new Result<>(Result.OK,"删除成功");
    }
    @PostMapping("/queryApiCount")
    public Result<Integer> queryGroupCount(@RequestBody Map<String,Object> map) throws Exception {
        Integer total = gatewayApiService.selectCount(map);
        Integer count = gatewayProjectGroupService.selectCount2(map);
        return new Result<Integer>(Result.OK,"查询成功","",total,count);
    }
}
