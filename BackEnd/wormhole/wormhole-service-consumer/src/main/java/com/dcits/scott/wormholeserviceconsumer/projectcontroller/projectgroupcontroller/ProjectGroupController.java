package com.dcits.scott.wormholeserviceconsumer.projectcontroller.projectgroupcontroller;

import com.dcits.scott.gateway.pojo.GatewayApiGroupDO;
import com.dcits.scott.gateway.pojo.GatewayProjectGroupDO;
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
@RequestMapping("/projectGroup")
public class ProjectGroupController {
    @Reference
    GatewayProjectGroupService gatewayProjectGroupService;
    @Reference
    GatewayApiGroupService gatewayApiGroupService;

    @PostMapping("/queryProjectAndGroup")
    public Result<List<GatewayApiGroupDO>> queryProjectAndGroup(@RequestBody Map<String,Object> map) throws Exception {
        List<GatewayApiGroupDO> gatewayApiGroupDOList = gatewayApiGroupService.selectList(map);
        Integer projectId = Integer.parseInt(String.valueOf(map.get("projectId")));
        List<GatewayProjectGroupDO> gatewayProjectGroupDOList=gatewayProjectGroupService.selectByProjectId(projectId);
        Map<String,Integer> map1 = new HashMap<>();
        for (GatewayProjectGroupDO gatewayProjectGroupDO: gatewayProjectGroupDOList
             ) {
            Integer groupId = gatewayProjectGroupDO.getGroupId();
            map1.put(groupId+"",groupId);
        }
        for (GatewayApiGroupDO gatewayApiGroupDO:gatewayApiGroupDOList
             ) {
            if (map1.containsKey(gatewayApiGroupDO.getId()+"")){
                gatewayApiGroupDO.setAuthorized(true);
            }else gatewayApiGroupDO.setAuthorized(false);
        }
        return new Result<>("","",gatewayApiGroupDOList);
    }
    @PostMapping("/insertAuthorized")
    public Result<String> insertAuthorized(@RequestBody Map<String,Object> map){
        gatewayProjectGroupService.insertByProGou(map);
        return new Result<>("200","插入成功");
    }
    @PostMapping("/deleteAuthorized")
    public Result<String> deleteAuthorized(@RequestBody Map<String,Object> map){
        gatewayProjectGroupService.deleteByProGou(map);
        return new Result<>("200","删除成功");
    }
    @PostMapping("/queryGroupCount")
    public Result<Integer> queryGroupCount(@RequestBody Map<String,Object> map) throws Exception {
        Integer total = gatewayApiGroupService.selectCount(map);
        Integer count = gatewayProjectGroupService.selectCount(map);
        return new Result<Integer>("200","查询成功","",total,count);
    }

}
