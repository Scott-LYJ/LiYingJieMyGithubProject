package com.dcits.scott.roleprovider.resource.mapper;

import com.dcits.scott.admin.pojo.AuthResource;
import com.dcits.scott.roleprovider.common.ProtoMapper;
import org.springframework.stereotype.Repository;

/**
 * @author fendyguo
 * @date 2018年09月13日 下午7:26:45
 */
@Repository
public abstract interface ResourceMapper extends ProtoMapper<AuthResource> {
  /**
   * 根据父节点删除子节点信息
   * @param paramLong
   * @throws Exception
   */
  public abstract void deleteChildrenByPID(int paramLong) throws Exception;
}
