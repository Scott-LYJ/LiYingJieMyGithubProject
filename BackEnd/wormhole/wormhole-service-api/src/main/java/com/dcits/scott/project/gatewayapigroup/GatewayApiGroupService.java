package com.dcits.scott.project.gatewayapigroup;
import com.dcits.scott.gateway.pojo.GatewayApiGroupDO;
import com.dcits.scott.project.common.CommonService;

import java.util.Map;

/**
 * 
 * API分组表 接口
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
public interface GatewayApiGroupService extends CommonService<GatewayApiGroupDO> {


    void insertByMap(Map<String,Object> map);

    void delSysGroupByIds(Map<String,Object> map);

    void updateGroupByMap(Map<String,Object> map);

    GatewayApiGroupDO selectId(Long id);
}