package com.dcits.scott.auth.authpermission;

import com.dcits.scott.admin.pojo.AuthAuthorization;
import com.dcits.scott.admin.pojo.AuthPermission;
import com.dcits.scott.auth.common.ProtoService;

import java.util.List;
import java.util.Map;

/**
 * 权限认证服务接口
 * @author scott lee
 * @date 2020年02月06日
 */
public abstract interface PermissionService extends ProtoService<AuthPermission> {
    /**
     * 查询权限
     * @param paramInteger
     * @param paramString
     * @throws Exception
     */
    public abstract void authz(Integer paramInteger, String paramString) throws Exception;

    /**
     * 查询权限列表
     * @param paramInteger
     * @return
     * @throws Exception
     */
    public abstract List<AuthPermission> selectByRoleId(Integer paramInteger) throws Exception;

    List<AuthPermission> querPermissionList(List<Integer> roleIds);

    void insertList(Map<String,Object> map);

    void deleteByRoleId(int roleId);
}
