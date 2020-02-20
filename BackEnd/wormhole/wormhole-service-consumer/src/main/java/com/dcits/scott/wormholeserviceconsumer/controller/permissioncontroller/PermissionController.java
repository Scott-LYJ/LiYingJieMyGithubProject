package com.dcits.scott.wormholeserviceconsumer.controller.permissioncontroller;

import com.dcits.scott.admin.pojo.AuthPermission;
import com.dcits.scott.support.result.Result;
import com.dcits.scott.auth.authpermission.PermissionService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/permission")
public class PermissionController {
    @Reference
    PermissionService permissionService;

    @PostMapping("/getSysAclList")
    public Result<List<Integer>> getSysAclList(@RequestBody Map<String,Object> map) throws Exception {
       Integer roleId = Integer.parseInt(String.valueOf(map.get("roleId")));
        List<AuthPermission> permissions = permissionService.selectByRoleId(roleId);
        List<Integer> list = new ArrayList<>();
        for (AuthPermission authPermission:permissions){
            list.add(authPermission.getResId());
        }
        return new Result<>("200","查询成功",list);
    }
    @PostMapping("/insertSysAcl")
    public Result<String> insertSysAcl(@RequestBody Map<String,Object> map){
        //先删
        permissionService.deleteByRoleId(Integer.parseInt(String.valueOf(map.get("roleId"))));
        permissionService.insertList(map);
        return new Result<>("200","插入成功","");
    }
}
