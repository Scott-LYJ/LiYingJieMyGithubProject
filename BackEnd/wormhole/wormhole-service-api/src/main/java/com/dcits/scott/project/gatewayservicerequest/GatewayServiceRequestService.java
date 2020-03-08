package com.dcits.scott.project.gatewayservicerequest;
import com.dcits.scott.gateway.pojo.GatewayServiceRequestDO;
import com.dcits.scott.project.common.CommonService;

import java.util.List;

/**
 * 
 * API请求参数表 接口
 * 
 * @version 
 * @author chenhailong
 * <pre>
 * Author	Version		Date		Changes
 * chenhailong 	1.0  2018年06月22日 Created
 *
 * </pre>
 * @since 1.
 */
public interface GatewayServiceRequestService extends CommonService<GatewayServiceRequestDO> {

    List<GatewayServiceRequestDO> selectByApiId(Long apiId);

    void updateByPrimaryKeySelective(GatewayServiceRequestDO gatewayServiceRequestDO);
}