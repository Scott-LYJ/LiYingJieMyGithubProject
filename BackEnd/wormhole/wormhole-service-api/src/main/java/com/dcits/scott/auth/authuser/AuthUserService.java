package com.dcits.scott.auth.authuser;

import com.dcits.scott.admin.pojo.AuthAuthorization;
import com.dcits.scott.admin.pojo.AuthUser;
import com.dcits.scott.auth.common.ProtoService;

import java.util.List;
import java.util.Map;

public interface AuthUserService  {
    //test
    String testMybatis(Integer id);
    //登录认证
    AuthUser loginAuthentication(AuthUser authUser);
    //查询用户列表
    List<AuthUser> querySysUserList(Map<String,Object> map);

    Integer  selectCount();
}
