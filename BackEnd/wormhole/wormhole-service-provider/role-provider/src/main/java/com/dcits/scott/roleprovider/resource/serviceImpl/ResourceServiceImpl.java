package com.dcits.scott.roleprovider.resource.serviceImpl;

import com.dcits.scott.admin.pojo.AuthResource;
import com.dcits.scott.auth.authresource.ResourceService;
import com.dcits.scott.roleprovider.common.ProtoMapper;
import com.dcits.scott.roleprovider.common.ProtoServiceImpl;
import com.dcits.scott.roleprovider.resource.mapper.ResourceMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author fendyguo
 * @date 2018年09月13日 下午7:26:45
 */
@Service
public class ResourceServiceImpl extends ProtoServiceImpl<AuthResource> implements ResourceService {
  @javax.annotation.Resource
  private ResourceMapper resourceMapper;

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
