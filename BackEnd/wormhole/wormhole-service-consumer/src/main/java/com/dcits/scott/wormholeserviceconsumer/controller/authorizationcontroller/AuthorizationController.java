package com.dcits.scott.wormholeserviceconsumer.controller.authorizationcontroller;

import com.dcits.scott.admin.pojo.AuthAuthorization;
import com.dcits.scott.admin.pojo.AuthUser;
import com.dcits.scott.support.result.Result;
import com.dcits.scott.auth.authauthorization.AuthorizationService;
import com.dcits.scott.auth.authuser.AuthUserService;
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

    @GetMapping("/hello")
    public String hello(){
        return "success";

    }

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
        System.out.println("312321");
        authorizationService.insertList(authAuthorizationList);

        return new Result<String>("","","");
    }
    @PostMapping("/getRoleUserList")
    public Result<List<AuthUser>> getRoleUserList(@RequestBody Map<String,Object> map) throws Exception {
        Map<String,Object> map1 = new HashMap<>();
        map1.put("skip",map.get("skip"));
        map1.put("size",map.get("size"));
        List<AuthAuthorization> authAuthorizations = authorizationService.selectList(map);
        List<Integer> userIds = new ArrayList<>();
        for (AuthAuthorization authAuthorization: authAuthorizations
             ) {
            Integer userId = authAuthorization.getUserId();
            userIds.add(userId);
        }
        map1.put("userIds",userIds);
       // authUserService.querySysUserList()
        List<AuthUser> authUsers=authUserService.selectByList(map1);
        Integer count = authUsers.size();

        return new Result<List<AuthUser>>("200","查询成功","",authUsers,count);
    }
    @PostMapping("/deleteSysRoleUserByid")
    public Result<String> deleteSysROleUserByid(@RequestBody Map<String,Object> map){
        authorizationService.delUserAuthorizationById(map);
       // authUserService.delSysUserByUserId(map);
        return new Result<>("","");
    }
}
