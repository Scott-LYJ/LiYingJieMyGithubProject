package com.dcits.scott.projectprovider.gatewayapigroup.mapper;

import com.dcits.scott.gateway.pojo.GatewayApiGroupDO;
import com.dcits.scott.project.common.CommonMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 网关api分组mapper
 */
@Repository
public interface GatewayApiGroupMapper extends CommonMapper<GatewayApiGroupDO> {
  /**
   * 查询GROUP分页信息
   * @param map
   * @return
   */
  List<GatewayApiGroupDO> selectGroupPage(Map<String, Object> map);

  void insertByMap(Map<String,Object> map);

  void delSysGroupByIds(Map<String,Object> map);

  void updateGroupByMap(Map<String,Object> map);

  GatewayApiGroupDO selectId(Long id);
}