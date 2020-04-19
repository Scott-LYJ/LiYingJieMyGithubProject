package com.dcits.scott.roleprovider.permission.mapper;

import com.dcits.scott.admin.pojo.AuthPermission;
import com.dcits.scott.auth.common.ProtoMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public abstract interface PermissionMapper extends ProtoMapper<AuthPermission> {
  /**
   * 根据角色id查询权限
   * @param paramInteger
   * @return
   * @throws Exception
   */
  public abstract List<AuthPermission> selectByRoleId(Integer paramInteger) throws Exception;

  /**
   * 根据橘色ID删除信息
   * @param paramInteger
   * @throws Exception
   */
  public abstract void deleteByRoleId(Integer paramInteger) throws Exception;

  /**
   * 插入数据
   * @param paramList
   * @throws Exception
   */
  public abstract void insertList(List<AuthPermission> paramList) throws Exception;

    List<AuthPermission> querPermissionList(List<Integer> roleIds);
    void insertList(Map<String,Object> map);
}
