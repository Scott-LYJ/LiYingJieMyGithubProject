package com.dcits.scott.interfaceprovider.gatewayservicerequest.mapper;

import com.dcits.scott.gateway.pojo.GatewayServiceRequestDO;
import com.dcits.scott.project.common.CommonMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 网关请求参数 mapper类
 */
@Repository
public interface GatewayServiceRequestMapper extends CommonMapper<GatewayServiceRequestDO> {
    
    /**
     * 根据条件查询api的参数列表信息
     * @param params 查询条件
     * @return
     */
    List<GatewayServiceRequestDO> selectParamsList(Map<String, Object> params);
    
    /**
     * 根据apiid 删除接口的参数信息
     * @param id api - id
     * @return
     * @throws Exception
     */
    int deleteByApi(Long id) throws Exception;

    void batchInsert(List<GatewayServiceRequestDO> list);

    List<GatewayServiceRequestDO> selectByApiId(Long apiId);
}