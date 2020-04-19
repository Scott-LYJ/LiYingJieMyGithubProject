package com.dcits.scott.project.gatewayproject;
import com.dcits.scott.gateway.pojo.GatewayApiGroupDO;
import com.dcits.scott.gateway.pojo.GatewayProjectDO;
import com.dcits.scott.project.common.CommonService;

import java.util.List;
import java.util.Map;

/**
 * 
 * API分组表 接口
 */
public interface GatewayProjectService extends CommonService<GatewayProjectDO> {

    public String hello();

    List<GatewayProjectDO> queryProjectList(Map<String,Object> map);

    void deleteBatch(Map<String,Object> map);
    void insert(Map<String,Object> map);

    void updateByMap(Map<String,Object> map);

    void deleteByPrimaryKey(Integer id);

    GatewayProjectDO selectByIntId(int id);
}