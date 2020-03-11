package com.dcits.scott.projectprovider.gatewayprojectgroup.mapper;

import com.dcits.scott.gateway.pojo.GatewayProjectGroupDO;
import com.dcits.scott.project.common.CommonMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface GatewayProjectGroupMapper extends CommonMapper<GatewayProjectGroupDO> {
    int deleteByPrimaryKey(Integer id);

    int insert(GatewayProjectGroupDO record);

    GatewayProjectGroupDO selectByPrimaryKey(Integer id);

    List<GatewayProjectGroupDO> selectAll();

    int updateByPrimaryKey(GatewayProjectGroupDO record);

    List<GatewayProjectGroupDO> selectByProjectId(Integer projectId);

    void insertByProGou(Map<String,Object> map);

    void deleteByProGou(Map<String,Object> map);

    void deleteByProApi(Map<String,Object> map);

    void insertByProApi(Map<String,Object> map);

    Integer selectCount2(Map<String,Object> map);
}