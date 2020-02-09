package com.dcits.scott.roleprovider.role.serviceImpl;

import com.dcits.scott.admin.pojo.AuthRole;
import com.dcits.scott.auth.authrole.RoleService;
import com.dcits.scott.roleprovider.common.ProtoMapper;
import com.dcits.scott.roleprovider.common.ProtoServiceImpl;
import com.dcits.scott.roleprovider.role.mapper.RoleMapper;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;

/**
 * @author fendyguo
 * @date 2018年09月13日 下午7:26:45
 */
@Service
public class RoleServiceImpl extends ProtoServiceImpl<AuthRole> implements RoleService {
  @Resource
  private RoleMapper roleMapper;

  @Override
  protected ProtoMapper<AuthRole> getMapper() {
    return this.roleMapper;
  }
}
