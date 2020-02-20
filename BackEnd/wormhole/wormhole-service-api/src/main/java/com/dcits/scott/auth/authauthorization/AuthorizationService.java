package com.dcits.scott.auth.authauthorization;

import com.dcits.scott.admin.pojo.AuthAuthorization;
import com.dcits.scott.auth.common.ProtoService;

import java.util.List;
import java.util.Map;

/**
 * 权限认证服务接口
 * @author scott lee
 * @date 2020年02月06日
 */
public interface AuthorizationService extends ProtoService<AuthAuthorization> {
    /**
     * 用户权限
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
    public abstract List<AuthAuthorization> selectByUserId(Integer paramInteger) throws Exception;


    void insertList(List<AuthAuthorization> authAuthorizationList) throws Exception;

    void delUserAuthorizationById(Map<String,Object> map);
}
