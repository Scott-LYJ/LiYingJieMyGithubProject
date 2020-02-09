package com.dcits.scott.wormholeserviceconsumer.controller.usercontroller;

import com.dcits.scott.admin.pojo.AuthUser;
import com.dcits.scott.admin.pojo.support.result.ResultInfo;
import com.dcits.scott.auth.authuser.AuthUserService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Reference
    AuthUserService authUserService;

    @PostMapping("/querySysUserList")
    public Map<String,Object> querySysUserList(@RequestBody Map<String,Object> map){
        //获取列表
        List<AuthUser> authUsers = authUserService.querySysUserList(map);
        //获取总条数
        Integer count = authUserService.selectCount();

        ResultInfo<AuthUser> resultInfo = new ResultInfo();
        resultInfo = new ResultInfo("SUCCESS","查询成功",authUsers,null,count,"");
        Map<String,Object> map1 = new HashMap<>();
        map1.put("result",resultInfo);
        return map1;

    }
}
