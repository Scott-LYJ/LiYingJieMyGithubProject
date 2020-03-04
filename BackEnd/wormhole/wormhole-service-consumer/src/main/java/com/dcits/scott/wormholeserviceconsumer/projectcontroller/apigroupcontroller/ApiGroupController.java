package com.dcits.scott.wormholeserviceconsumer.projectcontroller.apigroupcontroller;

import com.dcits.scott.gateway.pojo.GatewayApiGroupDO;
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


}
