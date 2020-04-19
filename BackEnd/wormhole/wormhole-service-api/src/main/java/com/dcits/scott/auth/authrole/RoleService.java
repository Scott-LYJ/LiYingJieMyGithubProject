package com.dcits.scott.auth.authrole;

import com.dcits.scott.admin.pojo.AuthRole;
import com.dcits.scott.auth.common.ProtoService;

import java.util.List;
import java.util.Map;

/**
 * 角色服务接口
 */
public abstract interface RoleService extends ProtoService<AuthRole> {
    List<AuthRole> queryRoleUserList(List<Integer> list);
}
