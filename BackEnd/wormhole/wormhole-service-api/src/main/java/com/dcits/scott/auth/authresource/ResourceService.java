package com.dcits.scott.auth.authresource;

import com.dcits.scott.admin.pojo.AuthAuthorization;
import com.dcits.scott.admin.pojo.AuthResource;
import com.dcits.scott.auth.common.ProtoService;

public abstract interface ResourceService extends ProtoService<AuthResource> {
    /**
     * 删除资源节点
     * @param paramInteger
     * @throws Exception
     */
    public abstract void deleteNode(Integer paramInteger) throws Exception;
}
