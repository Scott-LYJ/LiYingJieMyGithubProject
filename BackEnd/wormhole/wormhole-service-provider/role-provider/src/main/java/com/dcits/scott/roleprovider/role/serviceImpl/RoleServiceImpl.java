package com.dcits.scott.roleprovider.role.serviceImpl;

import com.dcits.scott.admin.pojo.AuthRole;
import com.dcits.scott.auth.authrole.RoleService;
import com.dcits.scott.auth.common.ProtoMapper;
import com.dcits.scott.auth.common.ProtoServiceImpl;
import com.dcits.scott.roleprovider.role.mapper.RoleMapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;
import javax.management.relation.Role;
import java.util.List;
import java.util.Map;


@Service
public class RoleServiceImpl extends ProtoServiceImpl<AuthRole> implements RoleService {
  @Resource
  private RoleMapper roleMapper;

  @HystrixCommand(commandProperties = {
          @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
          @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
  })


  @Override
  protected ProtoMapper<AuthRole> getMapper() {
    return this.roleMapper;
  }

  @Override
  public List<AuthRole> queryRoleUserList(List<Integer> list) {
    return roleMapper.queryRoleUserList(list);
  }

  @Override
  public List<AuthRole> selectList(Map<String, Object> map) throws Exception {
    return roleMapper.selectList(map);
  }
}
