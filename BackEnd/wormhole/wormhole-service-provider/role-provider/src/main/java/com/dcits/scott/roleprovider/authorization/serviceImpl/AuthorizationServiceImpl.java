package com.dcits.scott.roleprovider.authorization.serviceImpl;

import com.dcits.scott.admin.pojo.AuthAuthorization;
import com.dcits.scott.auth.authauthorization.AuthorizationService;
import com.dcits.scott.auth.common.ProtoMapper;
import com.dcits.scott.auth.common.ProtoServiceImpl;
import com.dcits.scott.roleprovider.authorization.mapper.AuthorizationMapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AuthorizationServiceImpl extends ProtoServiceImpl<AuthAuthorization> implements AuthorizationService {
  @Resource
  private AuthorizationMapper authorizationMapper;

  @HystrixCommand(commandProperties = {
          @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
          @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
  })


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
