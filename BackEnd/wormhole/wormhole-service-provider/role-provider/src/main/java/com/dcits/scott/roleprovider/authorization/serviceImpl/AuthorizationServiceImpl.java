package com.dcits.scott.roleprovider.authorization.serviceImpl;

import com.dcits.scott.admin.pojo.AuthAuthorization;
import com.dcits.scott.auth.authauthorization.AuthorizationService;
import com.dcits.scott.roleprovider.common.ProtoMapper;
import com.dcits.scott.roleprovider.common.ProtoServiceImpl;
import com.dcits.scott.roleprovider.authorization.mapper.AuthorizationMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author fendyguo
 * @date 2018年09月13日 下午7:26:45
 */
@Service
public class AuthorizationServiceImpl extends ProtoServiceImpl<AuthAuthorization> implements AuthorizationService {
  @Resource
  private AuthorizationMapper authorizationMapper;

  @Override
  protected ProtoMapper<AuthAuthorization> getMapper() {
    return this.authorizationMapper;
  }

  @Override
  @Transactional(rollbackFor = {Exception.class})
  public void authz(Integer userId, String roleIds) throws Exception {
    this.authorizationMapper.deleteByUserId(userId);

    List<AuthAuthorization> authorizationList = new ArrayList<AuthAuthorization>();
    String[] roleIdArr = roleIds.split("-");
    for (int i = 0; i < roleIdArr.length; i++) {
      AuthAuthorization authAuthorization = new AuthAuthorization();
      authAuthorization.setUserId(userId);
      authAuthorization.setRoleId(Integer.valueOf(roleIdArr[i]));

      authorizationList.add(authAuthorization);
    }
    this.authorizationMapper.insertList(authorizationList);
  }

  @Override
  public List<AuthAuthorization> selectByUserId(Integer userId) throws Exception {
    return this.authorizationMapper.selectByUserId(userId);
  }

  @Override
  public void insertList(List<AuthAuthorization> authAuthorizationList) throws Exception {
    this.authorizationMapper.insertList(authAuthorizationList);
  }

  @Override
  public void delUserAuthorizationById(Map<String, Object> map) {
    this.authorizationMapper.delUserAuthorizationById(map);
  }

}
