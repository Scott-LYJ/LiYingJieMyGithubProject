package com.dcits.scott.roleprovider.permission.serviceImpl;

import com.dcits.scott.admin.pojo.AuthPermission;
import com.dcits.scott.auth.authpermission.PermissionService;
import com.dcits.scott.auth.common.ProtoMapper;
import com.dcits.scott.auth.common.ProtoServiceImpl;
import com.dcits.scott.roleprovider.permission.mapper.PermissionMapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 权限服务接口
 */
@Service
public class PermissionServiceImpl extends ProtoServiceImpl<AuthPermission> implements PermissionService {
  @Resource
  private PermissionMapper permissionMapper;

  @HystrixCommand(commandProperties = {
          @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
          @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
  })


  @Override
  protected ProtoMapper<AuthPermission> getMapper() {
    return this.permissionMapper;
  }

  @Override
  @Transactional(rollbackFor = {Exception.class})
  public void authz(Integer roleId, String resourceIds) throws Exception {
    this.permissionMapper.deleteByRoleId(roleId);

    List<AuthPermission> permissionList = new ArrayList<AuthPermission>();
    String[] resourceArr = resourceIds.split("-");
    for (int i = 0; i < resourceArr.length; i++) {
      AuthPermission permission = new AuthPermission();
      permission.setRoleId(roleId);
      permission.setResId(Integer.valueOf(resourceArr[i]));

      permissionList.add(permission);
    }
    this.permissionMapper.insertList(permissionList);
  }

  @Override
  public List<AuthPermission> selectByRoleId(Integer roleId) throws Exception {
    return this.permissionMapper.selectByRoleId(roleId);
  }

  @Override
  public List<AuthPermission> querPermissionList(List<Integer> roleIds) {
    return permissionMapper.querPermissionList(roleIds);
  }

  @Override
  public void insertList(Map<String, Object> map) {
     permissionMapper.insertList(map);
  }

  @Override
  public void deleteByRoleId(int roleId) {
    try {
      permissionMapper.deleteByRoleId(roleId);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}


