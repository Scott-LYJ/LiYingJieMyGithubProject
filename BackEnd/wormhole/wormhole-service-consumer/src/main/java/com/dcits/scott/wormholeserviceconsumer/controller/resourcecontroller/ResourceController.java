package com.dcits.scott.wormholeserviceconsumer.controller.resourcecontroller;

import com.dcits.scott.admin.pojo.AuthPermission;
import com.dcits.scott.admin.pojo.AuthResource;
import com.dcits.scott.admin.pojo.AuthRole;
import com.dcits.scott.support.result.Result;
import com.dcits.scott.auth.authpermission.PermissionService;
import com.dcits.scott.auth.authresource.ResourceService;
import com.dcits.scott.auth.authrole.RoleService;
import com.dcits.scott.wormholeserviceconsumer.ExtendFunction;
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
@RequestMapping("/resource")
public class ResourceController {
    @Reference
    ResourceService resourceService;
    @Reference
    RoleService roleService;
    @Reference
    PermissionService permissionService;
    //查询资源列表
    @HystrixCommand(fallbackMethod = "getSysResourceListError",ignoreExceptions = HystrixBadRequestException.class)
    @PostMapping("/getSysResourceList")
    public Result<List<AuthResource>> getSysResourceList(@RequestBody Map<String,Object> map) throws Exception {
        List<AuthResource> resourceList = resourceService.selectList(map);
        map.clear();
        List<AuthResource> authResources= ExtendFunction.queryMenuList(resourceList,null);
        return new Result<>(Result.OK,"查询成功",authResources);
    }
    public Result<List<AuthResource>> getSysResourceListError(Map<String,Object> map,Throwable e){
        return new Result<>(Result.ERROR,"查询失败:"+e.getMessage(),null);

    }
    //获取每个角色所属的资源
    @HystrixCommand(fallbackMethod = "getSysAclListError",ignoreExceptions = HystrixBadRequestException.class)
    @PostMapping("/getSysAclList")
    public Result<List<AuthResource>> getSysAclList(@RequestBody Map<String,Object> map) throws Exception {
        List<Integer> roleIds = new ArrayList<>();
        List<AuthResource> resourceList;
        roleIds.add(Integer.parseInt(String.valueOf(map.get("roleId"))));
        List<AuthPermission> permissionList = new ArrayList<>();
        List<Integer> resourceIds = new ArrayList<>();
        permissionList = permissionService.querPermissionList(roleIds);
        for (AuthPermission permission : permissionList) {
            resourceIds.add(permission.getResId());
        }
        map.clear();
        map.put("resourceIds", resourceIds);
        resourceList = resourceService.selectList(map);
        resourceList= ExtendFunction.queryMenuList(resourceList,null);
        return new Result<>(Result.OK,"查询成功",resourceList);
    }
    public Result<List<AuthResource>> getSysAclListError(Map<String,Object> map,Throwable e){
        return new Result<>(Result.ERROR,"查询失败:"+e.getMessage(),null);

    }
}
