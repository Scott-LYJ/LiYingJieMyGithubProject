package com.dcits.scott.wormholeserviceconsumer.controller.authorizationcontroller;

import com.dcits.scott.admin.pojo.AuthAuthorization;
import com.dcits.scott.admin.pojo.AuthUser;
import com.dcits.scott.support.result.Result;
import com.dcits.scott.auth.authauthorization.AuthorizationService;
import com.dcits.scott.auth.authuser.AuthUserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/authorization")
public class AuthorizationController {
    @Reference
    AuthorizationService authorizationService;
    @Reference
    AuthUserService authUserService;

    //保存角色用户关系
    @HystrixCommand(fallbackMethod = "saveRoleUserError",ignoreExceptions = HystrixBadRequestException.class)
    @PostMapping("/saveRoleUser")
    public Result<String> saveRoleUser(@RequestBody Map<String,Object> map) throws Exception {
        List<AuthAuthorization> authAuthorizationList = new ArrayList<>();
        Integer roleId = Integer.parseInt(String.valueOf(map.get("roleId")));
        List<Integer> userIds = (List<Integer>) map.get("userIds");
        for (Integer userid: userIds
             ) {
            AuthAuthorization authAuthorization = new AuthAuthorization();
            authAuthorization.setRoleId(roleId);
            authAuthorization.setUserId(userid);
            authAuthorizationList.add(authAuthorization);
        }
        authorizationService.insertList(authAuthorizationList);
        return new Result<String>(Result.OK,"保存成功","");
    }
    public Result<String> saveRoleUserError(Map<String,Object> map,Throwable e){
        return new Result<>(Result.ERROR,"保存失败:"+e.getMessage(),"");
    }
    //获取角色用户列表
    @HystrixCommand(fallbackMethod = "getRoleUserListError",ignoreExceptions = HystrixBadRequestException.class)
    @PostMapping("/getRoleUserList")
    public Result<List<AuthUser>> getRoleUserList(@RequestBody Map<String,Object> map) throws Exception {
        Map<String,Object> map1 = new HashMap<>();
        map1.put("skip",map.get("skip"));
        map1.put("size",map.get("size"));
        List<AuthAuthorization> authAuthorizations = authorizationService.selectList(map);
        if(authAuthorizations.size()==0){
            return new Result<List<AuthUser>>(Result.OK,"查询成功","",new ArrayList<>(),0);
        }
        List<Integer> userIds = new ArrayList<>();
        for (AuthAuthorization authAuthorization: authAuthorizations
             ) {
            Integer userId = authAuthorization.getUserId();
            userIds.add(userId);
        }
        map1.put("userIds",userIds);
        List<AuthUser> authUsers=authUserService.selectByList(map1);
        Integer count = authUsers.size();
        return new Result<List<AuthUser>>(Result.OK,"查询成功","",authUsers,count);
    }
    public Result<List<AuthUser>> getRoleUserListError(Map<String,Object> map,Throwable e){
        return new Result<>(Result.ERROR,"查询失败:"+e.getMessage(),null);
    }
    //删除角色用户关系
    @HystrixCommand(fallbackMethod = "deleteSysRoleUserByidError",ignoreExceptions = HystrixBadRequestException.class)
    @PostMapping("/deleteSysRoleUserByid")
    public Result<String> deleteSysROleUserByid(@RequestBody Map<String,Object> map){
        authorizationService.delUserAuthorizationById(map);
        return new Result<>(Result.OK,"删除成功");
    }
    public Result<String> deleteSysRoleUserByidError(Map<String,Object> map,Throwable e){
        return new Result<>(Result.ERROR,"删除失败:"+e.getMessage(),null);
    }
    //
}
