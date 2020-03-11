package com.dcits.scott.wormholeserviceconsumer.projectcontroller.apigroupcontroller;

import com.alibaba.fastjson.JSON;
import com.dcits.scott.gateway.pojo.GatewayApiDO;
import com.dcits.scott.gateway.pojo.GatewayApiGroupDO;
import com.dcits.scott.project.gatewayapi.GatewayApiService;
import com.dcits.scott.project.gatewayapigroup.GatewayApiGroupService;
import com.dcits.scott.project.gatewayproject.GatewayProjectService;
import com.dcits.scott.support.result.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/group")
public class ApiGroupController {

   @Reference
    GatewayApiGroupService gatewayApiGroupService;
   @Reference
    GatewayApiService gatewayApiService;

    @PostMapping("/querySysGroupList")
    public Result<List<GatewayApiGroupDO>> querySysGroupList(@RequestBody Map<String,Object> map) throws Exception {
        try {
            List<GatewayApiGroupDO> gatewayApiGroupDOS = gatewayApiGroupService.selectPage(map);
            Integer count = gatewayApiGroupDOS.size();
            return new Result<>("200","查询成功","",gatewayApiGroupDOS,count);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>("500","查询失败",null);
        }

    }
    @PostMapping("/addSubmit")
    public Result<String> addSubmit(@RequestBody Map<String,Object> map){
        gatewayApiGroupService.insertByMap(map);
        return new Result<>("200","添加成功");
    }
    @PostMapping("/delSysGroupByIds")
    public Result<String> delSysGroupByIds(@RequestBody Map<String,Object> map){
        String ids = String.valueOf(map.get("ids"));
        String str[] = ids.split(",");
        List<String> strings =  Arrays.asList(str);
        map.put("ids",strings);
        gatewayApiGroupService.delSysGroupByIds(map);
        return new Result<>("200","添加成功");
    }
    @PostMapping("/updateGroup")
    public Result<String> updateGroup(@RequestBody Map<String,Object> map){
        gatewayApiGroupService.updateGroupByMap(map);
        return new Result<>("200","更新成功");
    }
    @PostMapping("/queryApi")
    public Result<List<GatewayApiDO>> queryApi(@RequestBody Map<String,Object> map){
        Long groupId = Long.parseLong(String.valueOf(map.get("group_id")));
        List<GatewayApiDO> gatewayApiDOS = gatewayApiService.selectSearchPage(map);
        for (GatewayApiDO gatewayApiDO:gatewayApiDOS){
            if (groupId==gatewayApiDO.getGroupId()){
                gatewayApiDO.setGroupauthorized(true);
            }else gatewayApiDO.setGroupauthorized(false);
        }
        return new Result<>("200","查询成功",gatewayApiDOS);
    }
    @PostMapping("/insertGroupAuthorized")
    public Result<String> insertAuthorized(@RequestBody String json){
        GatewayApiDO gatewayApiDO = JSON.parseObject(json, GatewayApiDO.class);
        try {
            gatewayApiService.update(gatewayApiDO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result<>("200","修改成功");
    }
    @PostMapping("/deleteGroupAuthorized")
    public Result<String> deleteAuthorized(@RequestBody String json){
        GatewayApiDO gatewayApiDO = JSON.parseObject(json, GatewayApiDO.class);

        try {
            gatewayApiService.update(gatewayApiDO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result<>("200","成功");
    }
    @PostMapping("/queryGroupApiCount")
    public Result<Integer> queryGroupCount(@RequestBody Map<String,Object> map) throws Exception {
        Integer total = gatewayApiGroupService.selectCount(map);
        Integer count = gatewayApiService.selectCount(map);
        return new Result<Integer>("200","查询成功","",total,count);
    }

}
