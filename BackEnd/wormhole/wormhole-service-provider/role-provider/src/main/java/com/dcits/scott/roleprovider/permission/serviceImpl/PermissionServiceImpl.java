package com.dcits.scott.roleprovider.permission.serviceImpl;

import com.dcits.scott.admin.pojo.AuthPermission;
import com.dcits.scott.auth.authpermission.PermissionService;
import com.dcits.scott.roleprovider.common.ProtoMapper;
import com.dcits.scott.roleprovider.common.ProtoServiceImpl;
import com.dcits.scott.roleprovider.permission.mapper.PermissionMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 权限服务接口
 * @author fendyguo
 * @date 2018年09月13日 下午7:26:45
 */
@Service
public class PermissionServiceImpl extends ProtoServiceImpl<AuthPermission> implements PermissionService {
  @Resource
  private PermissionMapper permissionMapper;

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
}
