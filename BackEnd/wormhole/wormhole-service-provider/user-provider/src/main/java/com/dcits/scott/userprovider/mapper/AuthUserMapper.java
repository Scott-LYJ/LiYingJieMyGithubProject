package com.dcits.scott.userprovider.mapper;

import com.alibaba.druid.sql.ast.statement.SQLForeignKeyImpl;
import com.dcits.scott.admin.pojo.AuthUser;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AuthUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AuthUser record);
    //根据主键查询
    AuthUser selectByPrimaryKey(Integer id);
    //登录认证
    AuthUser selectPasswordByName(AuthUser authUser);

    List<AuthUser> selectAll();

    int updateByPrimaryKey(AuthUser record);

    //用户列表查询
    List<AuthUser> querySysUserList(Map<String,Object> map);

    Integer selectCount();

    void delSysUserByUserId(Map<String,Object> map);

    Integer addUser(AuthUser authUser);

    void updateUser(AuthUser authUser);

    List<AuthUser> selectByList(Map<String,Object> map1);
}