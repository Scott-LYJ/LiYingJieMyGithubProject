package com.dcits.scott.projectprovider.gatewayproject.mapper;

import com.dcits.scott.gateway.pojo.GatewayProjectDO;
import com.dcits.scott.project.common.CommonMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface GatewayProjectMapper extends CommonMapper<GatewayProjectDO> {
    int deleteByPrimaryKey(Integer id);

    int insert(GatewayProjectDO record);
    int insert(Map<String,Object> map);

    GatewayProjectDO selectByPrimaryKey(Integer id);

    List<GatewayProjectDO> selectAll();

    int updateByPrimaryKey(GatewayProjectDO record);

    List<GatewayProjectDO> queryProjectList(Map<String,Object> map);

    void deleteBatch(Map<String,Object> map);

    void updateByMap(Map<String,Object> map);
}