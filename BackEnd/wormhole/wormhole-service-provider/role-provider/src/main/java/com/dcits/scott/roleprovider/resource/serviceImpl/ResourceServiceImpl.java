package com.dcits.scott.roleprovider.resource.serviceImpl;

import com.dcits.scott.admin.pojo.AuthResource;
import com.dcits.scott.auth.authresource.ResourceService;
import com.dcits.scott.auth.common.ProtoMapper;
import com.dcits.scott.auth.common.ProtoServiceImpl;
import com.dcits.scott.roleprovider.resource.mapper.ResourceMapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ResourceServiceImpl extends ProtoServiceImpl<AuthResource> implements ResourceService {
  @javax.annotation.Resource
  private ResourceMapper resourceMapper;

  @HystrixCommand(commandProperties = {
          @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
          @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
  })


  @Override
  protected ProtoMapper<AuthResource> getMapper() {
    return this.resourceMapper;
  }

  @Override
  @Transactional(rollbackFor = {Exception.class})
  public void deleteNode(Integer id) throws Exception {
    this.resourceMapper.delete(id);
    this.resourceMapper.deleteChildrenByPID(id);
  }
}
