package com.dcits.scott.wormholeserviceconsumer.controller.permissioncontroller;

import com.dcits.scott.admin.pojo.AuthPermission;
import com.dcits.scott.support.result.Result;
import com.dcits.scott.auth.authpermission.PermissionService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.exception.HystrixBadRequestException;
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
    //查看全部资源列表
    @HystrixCommand(fallbackMethod = "getSysAclListError",ignoreExceptions = HystrixBadRequestException.class)
    @PostMapping("/getSysAclList")
    public Result<List<Integer>> getSysAclList(@RequestBody Map<String,Object> map) throws Exception {
       Integer roleId = Integer.parseInt(String.valueOf(map.get("roleId")));
        List<AuthPermission> permissions = permissionService.selectByRoleId(roleId);
        List<Integer> list = new ArrayList<>();
        for (AuthPermission authPermission:permissions){
            list.add(authPermission.getResId());
        }
        return new Result<>(Result.OK,"查询成功",list);
    }
    public Result<List<Integer>> getSysAclListError(Map<String,Object> map,Throwable e){
        return new Result<>(Result.ERROR,"查询失败:"+e.getMessage(),null);
    }
    //修改角色资源列表
    @HystrixCommand(fallbackMethod = "insertSysAclError",ignoreExceptions = HystrixBadRequestException.class)
    @PostMapping("/insertSysAcl")
    public Result<String> insertSysAcl(@RequestBody Map<String,Object> map){
        //先删
        permissionService.deleteByRoleId(Integer.parseInt(String.valueOf(map.get("roleId"))));
        permissionService.insertList(map);
        return new Result<>(Result.OK,"插入成功","");
    }
    public Result<String> insertSysAclError(Map<String,Object> map,Throwable e){
        return new Result<>(Result.ERROR,"插入失败:"+e.getMessage(),"");
    }
}
