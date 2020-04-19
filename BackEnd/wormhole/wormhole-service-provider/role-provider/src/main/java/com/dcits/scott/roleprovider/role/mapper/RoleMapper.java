package com.dcits.scott.roleprovider.role.mapper;

import com.dcits.scott.admin.pojo.AuthRole;
import com.dcits.scott.auth.common.ProtoMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public abstract interface RoleMapper extends ProtoMapper<AuthRole> {
    List<AuthRole> queryRoleUserList(List<Integer> list);
}
