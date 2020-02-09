package com.dcits.scott.userprovider.serviceimpl;

import com.dcits.scott.admin.pojo.AuthUser;
import com.dcits.scott.auth.authuser.AuthUserService;
import com.dcits.scott.userprovider.mapper.AuthUserMapper;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class AuthUserServiceImpl implements AuthUserService {
    @Resource
    private AuthUserMapper authUserMapper;
    @Override
    public String testMybatis(Integer id) {
        AuthUser authUser = authUserMapper.selectByPrimaryKey(id);
        String s = authUser.toString();
        Subject subject = SecurityUtils.getSubject();

        return s+ subject.getSession().getAttribute("name")+subject.getSession().getAttributeKeys()+subject.getSession().getAttribute("sessionId");
    }
    //登录认证
    @Override
    public AuthUser loginAuthentication(AuthUser authUser) {
        AuthUser authUser1 = authUserMapper.selectPasswordByName(authUser);
        System.out.println(authUser1.toString());
        return authUser1;
    }

    @Override
    public List<AuthUser> querySysUserList(Map<String, Object> map) {

        List<AuthUser> authUsers = authUserMapper.querySysUserList(map);
        return authUsers;
    }

    @Override
    public Integer selectCount() {
        return authUserMapper.selectCount();
    }


}
