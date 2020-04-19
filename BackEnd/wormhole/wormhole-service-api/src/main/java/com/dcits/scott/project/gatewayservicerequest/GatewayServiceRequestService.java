package com.dcits.scott.project.gatewayservicerequest;
import com.dcits.scott.gateway.pojo.GatewayServiceRequestDO;
import com.dcits.scott.project.common.CommonService;

import java.util.List;

/**
 * 
 * API请求参数表 接口
 *
 */
public interface GatewayServiceRequestService extends CommonService<GatewayServiceRequestDO> {

    List<GatewayServiceRequestDO> selectByApiId(Long apiId);

    void updateByPrimaryKeySelective(GatewayServiceRequestDO gatewayServiceRequestDO);
}