package com.dcits.scott.roleprovider.authorization.mapper;

import com.dcits.scott.admin.pojo.AuthAuthorization;
import com.dcits.scott.auth.common.ProtoMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 */
@Repository
public abstract interface AuthorizationMapper extends ProtoMapper<AuthAuthorization> {
  
  /**
   * 根据用户id删除信息
   * @param paramInteger
   * @throws Exception
   */
  public abstract void deleteByUserId(Integer paramInteger) throws Exception;
  /**
   * 插入新
   * @param paramList
   * @throws Exception
   */
  public abstract void insertList(List<AuthAuthorization> paramList) throws Exception;

  /**
   * 根据用户ID查询认证信息
   * @param paramInteger
   * @return
   * @throws Exception
   */
  public abstract List<AuthAuthorization> selectByUserId(Integer paramInteger) throws Exception;


  void delUserAuthorizationById(Map<String,Object> map);
}

