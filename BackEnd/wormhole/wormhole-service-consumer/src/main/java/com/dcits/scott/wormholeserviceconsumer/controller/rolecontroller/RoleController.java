package com.dcits.scott.wormholeserviceconsumer.controller.rolecontroller;

import com.dcits.scott.admin.pojo.AuthAuthorization;
import com.dcits.scott.admin.pojo.AuthRole;
import com.dcits.scott.support.result.Result;
import com.dcits.scott.auth.authauthorization.AuthorizationService;
import com.dcits.scott.auth.authrole.RoleService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Reference
    RoleService roleService;
    @Reference
    AuthorizationService authorizationService;
    //根据角色查找用户
    @HystrixCommand(fallbackMethod = "queryRoleUserListError",ignoreExceptions = HystrixBadRequestException.class)
    @PostMapping("/queryRoleUserList")
    public Result<List<AuthRole>> queryRoleUserList(@RequestBody Map<String,Object> map) throws Exception {
        Integer id =(Integer) map.get("id");
        List<AuthAuthorization> authAuthorizations = authorizationService.selectByUserId(id);
        List<Integer> roleIds = new ArrayList<>();
        for (AuthAuthorization authAuthorization:authAuthorizations){
            roleIds.add(authAuthorization.getRoleId());
        }
        Map<String,Object> map1 = new HashMap<>();
        map1.put("id",roleIds);
        List<AuthRole> authRoles = roleService.queryRoleUserList(roleIds);
        return new Result<List<AuthRole>>(Result.OK,"查询成功",authRoles);
    }
    public Result<List<AuthRole>> queryRoleUserListError(Map<String,Object> map,Throwable e){
        return new Result<List<AuthRole>>(Result.ERROR,"查询失败:"+e.getMessage(),null);
    }
    //查看角色列表
    @HystrixCommand(fallbackMethod = "querySysRoleListError",ignoreExceptions = HystrixBadRequestException.class)
    @PostMapping("/querySysRoleList")
    public Result<List<AuthRole>> querySysRoleList(@RequestBody Map<String,Object> map) throws Exception {
        List<AuthRole> authRoles = roleService.selectList(map);
        Integer count = authRoles.size();
        return new Result<List<AuthRole>>(Result.OK,"查询成功","",authRoles,count);
    }
    public Result<List<AuthRole>> querySysRoleListError(Map<String,Object> map,Throwable e){
        return new Result<List<AuthRole>>(Result.ERROR,"查询失败:"+e.getMessage(),null);
    }
    //添加角色
    @HystrixCommand(fallbackMethod = "addRoleError",ignoreExceptions = HystrixBadRequestException.class)
    @PostMapping("/addRole")
    public Result<String> addRole(@RequestBody Map<String,Object> map) throws Exception {
        AuthRole authRole = new AuthRole();
        authRole.setName(String.valueOf(map.get("name")));
        authRole.setDes(String.valueOf(map.get("des")));
        roleService.insert(authRole);
        return new Result<>(Result.OK,"添加角色成功",null);
    }
    public Result<String> addRoleError(Map<String,Object> map,Throwable e){
        return new Result<String>(Result.ERROR,"查询失败:"+e.getMessage(),null);
    }
    //编辑角色
    @HystrixCommand(fallbackMethod = "editRoleError",ignoreExceptions = HystrixBadRequestException.class)
    @PostMapping("/editRole")
    public Result<String> editRole(@RequestBody Map<String,Object> map) throws Exception {
        AuthRole authRole = new AuthRole();
        authRole.setId(Integer.parseInt(String.valueOf(map.get("id"))));
        authRole.setName(String.valueOf(map.get("name")));
        authRole.setDes(String.valueOf(map.get("des")));
        roleService.update(authRole);
        return new Result<>(Result.OK,"修改角色成功",null);
    }
    public Result<String> editRoleError(Map<String,Object> map,Throwable e){
        return new Result<String>(Result.ERROR,"修改角色失败:"+e.getMessage(),null);
    }
    //删除角色
    @HystrixCommand(fallbackMethod = "deleteSysRoleByidError",ignoreExceptions = HystrixBadRequestException.class)
    @PostMapping("/deleteSysRoleByid")
    public Result<String> deleteSysRoleByid(@RequestBody Map<String,Object> map) throws Exception {
        roleService.delete(Integer.parseInt(String.valueOf(map.get("id"))));
        return new Result<>(Result.OK,"删除角色成功",null);
    }
    public Result<String> deleteSysRoleByidError(Map<String,Object> map,Throwable e){
        return new Result<String>(Result.ERROR,"删除角色失败:"+e.getMessage(),null);
    }
}
