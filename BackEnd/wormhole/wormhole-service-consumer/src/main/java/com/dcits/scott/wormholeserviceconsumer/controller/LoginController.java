package com.dcits.scott.wormholeserviceconsumer.controller;

import com.dcits.scott.admin.pojo.AuthUser;
import com.dcits.scott.support.result.Result;
import com.dcits.scott.auth.authauthorization.AuthorizationService;
import com.dcits.scott.auth.authpermission.PermissionService;
import com.dcits.scott.auth.authresource.ResourceService;
import com.dcits.scott.auth.authrole.RoleService;
import com.dcits.scott.auth.authuser.AuthUserService;
import com.dcits.scott.wormholeserviceconsumer.ExtendFunction;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class LoginController {
    private static final transient Logger log = LoggerFactory.getLogger(LoginController.class);
    @Reference
    AuthUserService authUserService;
    @Reference
    ResourceService resourceService;
    @Reference
    AuthorizationService authorizationService;
    @Reference
    PermissionService permissionService;
    @Reference
    RoleService roleService;
    // @RequiresRoles("admin")

    @GetMapping("/loginn")
    
    public AuthUser test(){
        AuthUser authUser = new AuthUser();
        authUser.setName("admin");
        AuthUser authUser1 = authUserService.loginAuthentication(authUser);
        return authUser1;
    }
    @RequiresPermissions("/auth/user/list")
    @ResponseBody
    public void test2(){
        System.out.println("权限");
    }

    @RequestMapping("/logout")
    @ResponseBody
    public String logout(){
        SecurityUtils.getSubject().logout();
        return "退出登录";
    }
    @PostMapping("/login")
   // @RequiresPermissions("/auth/user/lsessionist")
    @ResponseBody
    public Map<String,Object> login(@RequestBody Map<String,Object> map){

        //取出数据
        String loginName = (String) map.get("loginName");
        String password = (String) map.get("password");
        System.out.println(loginName+"----"+password);
        //获取当前用户
        Subject subject = SecurityUtils.getSubject();
        Session session = null;
        Object principal = null;
        PrincipalCollection previousPrincipals =null;
        //封装用户的登录数据
        UsernamePasswordToken token = new UsernamePasswordToken(loginName, password);
        //返回map
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            subject.login(token);                //执行登录操作，没有异常就OK

        } // 若没有指定的账户, 则 shiro 将会抛出 UnknownAccountException 异常.
        catch (UnknownAccountException uae) {
            log.info("----> There is no user with username of " + token.getPrincipal());
         //   return "code:1,msg:'没有指定的账户'";
            result.put("result",new Result<>(Result.ERROR,"1","没有指定的账户",""));
            return result;
        }
        // 若账户存在, 但密码不匹配, 则 shiro 会抛出 IncorrectCredentialsException 异常。
        catch (IncorrectCredentialsException ice) {
            log.info("----> Password for account " + token.getPrincipal() + " was incorrect!");
          //  return "code:2,msg:'账户存在, 但密码不匹配'";
            result.put("result",new Result<>(Result.ERROR,"2","账户存在, 但密码不匹配",""));
            return result;
        }
        // 用户被锁定的异常 LockedAccountException
        catch (LockedAccountException lae) {
            log.info("The account for username " + token.getPrincipal() + " is locked.  " +
                    "Please contact your administrator to unlock it.");
          //  return  "code:3,msg:'用户被锁定的异常'";
            result.put("result",new Result<>(Result.ERROR,"3","用户被锁定的异常",""));
            return result;
        }
        // ... catch more exceptions here (maybe custom ones specific to your application?
        // 所有认证时异常的父类.
        catch (AuthenticationException ae) {
            //unexpected condition?  error?
           // return "code:4,msg:'未知错误'";
            result.put("result",new Result<>(Result.ERROR,"4","没有指定的账户",""));
            return result;
        }
        return ExtendFunction.map(authorizationService,permissionService,resourceService,roleService);
//
    }

    /**
     * 未登录，shiro应重定向到登录界面，此处返回未登录状态信息由前端控制跳转页面
     * @return
     */
    @RequestMapping("/un_auth")
    public Map<String, Object> unAuth() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", "1000000");
        map.put("msg", "未登录");
        return map;
    }

    /**
     * 未授权，无权限，此处返回未授权状态信息由前端控制跳转页面
     * @return
     */
    @RequestMapping("/unauthorized")
    public Map<String,Object> unauthorized() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", "1000000");
        map.put("msg", "用户无权限");
        return map;
    }

}
