package com.dcits.scott.wormholeserviceconsumer;

import com.dcits.scott.TestInterface;
import com.dcits.scott.admin.pojo.AuthUser;
import com.dcits.scott.auth.authuser.AuthUserService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {
//   @Reference
//    TestInterface testInterface;
//    @Reference
//   AuthUserService authUserService;

//    @GetMapping("/hello")
//    public String sayHello(){
//        String s = testInterface.testInterface("1");
//        return s;
//    }
//    @GetMapping("/test")
//    public String test(@RequestParam(required = true) Integer id){
//        System.out.println("============"+id+"-------------");
//        String s = authUserService.testMybatis(id);
//        System.out.println(s+"ssssssssssssssssssssssssss");
//        return s;
//    }
    @GetMapping("/h")
    public String test(){

        return "redirect:/hello";
    }

//    @RequestMapping("/getAccount")
//    @ResponseBody
//    public String getAccount() {
//        Subject subject = SecurityUtils.getSubject();
//        AuthUser currentUser = (AuthUser) subject.getPrincipal();
//        System.out.println("登录用户姓名：" + currentUser.getName());
//        return currentUser.getName()+"    "+subject.getSession().getId();
//    }
    @RequiresPermissions("/auth/user/list")
    @GetMapping("test1")
    public String test1(){
        return "admin用户有此接口权限";
    }
    @RequiresPermissions("/test2")
    @GetMapping("test2")
    public String test2(){
        return "admin用户无此接口权限";
    }
    @GetMapping("test3")
    public String test3(){
        return "登录后可以任意访问此接口";
    }

}
