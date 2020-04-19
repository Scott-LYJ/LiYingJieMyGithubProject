package com.dcits.scott.project.gatewayprojectgroup;
import com.dcits.scott.gateway.pojo.GatewayApiGroupDO;
import com.dcits.scott.gateway.pojo.GatewayProjectDO;
import com.dcits.scott.gateway.pojo.GatewayProjectGroupDO;
import com.dcits.scott.project.common.CommonService;

import java.util.List;
import java.util.Map;

/**
 * API分组表 接口
 */
public interface GatewayProjectGroupService extends CommonService<GatewayProjectGroupDO> {


    List<GatewayProjectGroupDO> selectByProjectId(Integer projectId);

    void insertByProGou(Map<String,Object> map);

    void deleteByProGou(Map<String,Object> map);

    void deleteByProApi(Map<String,Object> map);

    void insertByProApi(Map<String,Object> map);

    Integer selectCount2(Map<String,Object> map);
}