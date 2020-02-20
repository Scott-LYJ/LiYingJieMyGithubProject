package com.dcits.scott.wormholeserviceconsumer.controller.rolecontroller;

import com.dcits.scott.admin.pojo.AuthAuthorization;
import com.dcits.scott.admin.pojo.AuthRole;
import com.dcits.scott.support.result.Result;
import com.dcits.scott.auth.authauthorization.AuthorizationService;
import com.dcits.scott.auth.authrole.RoleService;
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

    @GetMapping("/hello")
    public String hello(){
        return "success";

    }

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
        //List<AuthRole> authRoles = roleService.selectList(map1);
        List<AuthRole> authRoles = roleService.queryRoleUserList(roleIds);
        return new Result<List<AuthRole>>("200","查询成功",authRoles);
    }
    @PostMapping("/querySysRoleList")
    public Result<List<AuthRole>> querySysRoleList(@RequestBody Map<String,Object> map) throws Exception {
        List<AuthRole> authRoles = roleService.selectList(map);
        Integer count = authRoles.size();
        return new Result<List<AuthRole>>("200","查询成功","",authRoles,count);
    }
    @PostMapping("/addRole")
    public Result<String> addRole(@RequestBody Map<String,Object> map) throws Exception {
        AuthRole authRole = new AuthRole();
        authRole.setName(String.valueOf(map.get("name")));
        authRole.setDes(String.valueOf(map.get("des")));
        authRole.setCdt(new Date());
        authRole.setUdt(new Date());

            roleService.insert(authRole);

        return new Result<>("200","添加角色成功",null);
    }
    @PostMapping("/editRole")
    public Result<String> editRole(@RequestBody Map<String,Object> map) throws Exception {
        AuthRole authRole = new AuthRole();
        authRole.setId(Integer.parseInt(String.valueOf(map.get("id"))));
        authRole.setName(String.valueOf(map.get("name")));
        authRole.setDes(String.valueOf(map.get("des")));
        roleService.update(authRole);
        return new Result<>("200","修改角色成功",null);
    }
    @PostMapping("/deleteSysRoleByid")
    public Result<String> deleteSysRoleByid(@RequestBody Map<String,Object> map) throws Exception {
        roleService.delete(Integer.parseInt(String.valueOf(map.get("id"))));
        return new Result<>("200","修改角色成功",null);
    }
}
