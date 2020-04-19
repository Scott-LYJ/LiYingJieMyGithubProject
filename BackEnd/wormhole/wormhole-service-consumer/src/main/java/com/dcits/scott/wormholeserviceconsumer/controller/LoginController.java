package com.dcits.scott.wormholeserviceconsumer.controller;

import com.dcits.scott.admin.pojo.AuthUser;
import com.dcits.scott.support.result.Result;
import com.dcits.scott.auth.authauthorization.AuthorizationService;
import com.dcits.scott.auth.authpermission.PermissionService;
import com.dcits.scott.auth.authresource.ResourceService;
import com.dcits.scott.auth.authrole.RoleService;
import com.dcits.scott.auth.authuser.AuthUserService;
import com.dcits.scott.util.CacheUser;
import com.dcits.scott.wormholeserviceconsumer.ExtendFunction;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.crazycake.shiro.RedisSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

    @Autowired
    @Qualifier("redisSessionDAO")
    private RedisSessionDAO sessionDAO;


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
    //登录

    @HystrixCommand(fallbackMethod = "loginError",ignoreExceptions=HystrixBadRequestException.class)
    @PostMapping("/login")
    @ResponseBody
    public Map<String,Object> login(@RequestBody Map<String,Object> map){
        //取出数据
        String loginName = (String) map.get("loginName");
        String password = (String) map.get("password");

        Collection<Session> sessions = sessionDAO.getActiveSessions(); // 获取存在的所有SESSION账号
        log.info("There are {} several caches "+ sessions.size());

        Iterator<Session> it = sessions.iterator();
        while (it.hasNext()) {                                       // session 进行遍历
            Session session = it.next();
            SimplePrincipalCollection simplePrincipalCollection = (SimplePrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            log.info("There are ={}= simplePrincipalCollection "+simplePrincipalCollection);
            if (Objects.nonNull(simplePrincipalCollection)) {        // 如果不为null 则判断账号密码是否一样，一样的剔除前个登陆账号
                AuthUser user = (AuthUser) simplePrincipalCollection.getPrimaryPrincipal();
                if (user.getName().equals(loginName) ) {
                    session.setTimeout(200);// 注意，这里不能设置为0，如果设置为0，会下面shiro授权报错的
                    sessionDAO.delete(session);
                }
            }
        }
        //获取当前用户
        Subject subject = SecurityUtils.getSubject();
        CacheUser cacheUser;
        //封装用户的登录数据
        UsernamePasswordToken token = new UsernamePasswordToken(loginName, password);
        //返回map
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            subject.login(token);                //执行登录操作，没有异常就OK
            // 构建缓存用户信息返回给前端
            AuthUser user = (AuthUser) subject.getPrincipals().getPrimaryPrincipal();
            cacheUser =new  CacheUser();
            cacheUser.setToken(subject.getSession().getId().toString());
            BeanUtils.copyProperties(user, cacheUser);
            log.info("CacheUser is {}"+ cacheUser.toString());
        } // 若没有指定的账户, 则 shiro 将会抛出 UnknownAccountException 异常.
        catch (UnknownAccountException uae) {
            log.info("----> There is no user with username of " + token.getPrincipal());
         //   return "code:1,msg:'没有指定的账户'";
            result.put("result",new Result<>(Result.ERROR,"501","没有指定的账户",""));
            return result;
        }
        // 若账户存在, 但密码不匹配, 则 shiro 会抛出 IncorrectCredentialsException 异常。
        catch (IncorrectCredentialsException ice) {
            log.info("----> Password for account " + token.getPrincipal() + " was incorrect!");
          //  return "code:2,msg:'账户存在, 但密码不匹配'";
            result.put("result",new Result<>(Result.ERROR,"502","账户存在, 但密码不匹配",""));
            return result;
        }
        // 用户被锁定的异常 LockedAccountException
        catch (LockedAccountException lae) {
            log.info("The account for username " + token.getPrincipal() + " is locked.  " +
                    "Please contact your administrator to unlock it.");
          //  return  "code:3,msg:'用户被锁定的异常'";
            result.put("result",new Result<>(Result.ERROR,"503","用户被锁定的异常",""));
            return result;
        }
        // ... catch more exceptions here (maybe custom ones specific to your application?
        // 所有认证时异常的父类.
        catch (AuthenticationException ae) {
            //unexpected condition?  error?
           // return "code:4,msg:'未知错误'";
            result.put("result",new Result<>(Result.ERROR,"504","没有指定的账户",""));
            return result;
        }
        Map<String,Object> map1 = ExtendFunction.map(authorizationService,permissionService,resourceService,roleService);
        map1.put("token",cacheUser.getToken());
        return map1;
//
    }
    public Map<String,Object> loginError(Map<String,Object> map,Throwable e){
        map.put("result",new Result<>(Result.ERROR,"506","登录失败，出现异常",e.getMessage()));
        return  map;
    }
    //
    /**
     * 未登录，shiro应重定向到登录界面，此处返回未登录状态信息由前端控制跳转页面
     * @return
     */
    @RequestMapping("/un_auth")
    public Map<String, Object> unAuth() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("result",new Result<>(Result.ERROR,"505","未登录或已在其他地方登陆",""));
        return map;
    }

    /**
     * 未授权，无权限，此处返回未授权状态信息由前端控制跳转页面
     * @return
     */
    @RequestMapping("/unauthorized")
    public Map<String,Object> unauthorized() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("result",new Result<>(Result.ERROR,"506","用户无权限",""));
        return map;
    }

}
