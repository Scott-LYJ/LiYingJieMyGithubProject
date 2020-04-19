package com.dcits.scott.roleprovider.resource.mapper;

import com.dcits.scott.admin.pojo.AuthResource;
import com.dcits.scott.auth.common.ProtoMapper;
import org.springframework.stereotype.Repository;


@Repository
public abstract interface ResourceMapper extends ProtoMapper<AuthResource> {
  /**
   * 根据父节点删除子节点信息
   * @param paramLong
   * @throws Exception
   */
  public abstract void deleteChildrenByPID(int paramLong) throws Exception;
}
