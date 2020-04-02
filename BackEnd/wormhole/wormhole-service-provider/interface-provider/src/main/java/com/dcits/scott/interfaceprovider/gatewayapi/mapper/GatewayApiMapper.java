package com.dcits.scott.interfaceprovider.gatewayapi.mapper;

import com.dcits.scott.gateway.pojo.GatewayApiDO;
import com.dcits.scott.gateway.pojo.GatewayMessageDO;
import com.dcits.scott.project.common.CommonMapper;
import com.dcits.scott.support.entity.DocumentTimeCount;
import com.dcits.scott.support.entity.InterfaceTimeCount;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 网关api mapper类
 * @author chenhailong
 * @date 2019年2月14日 下午2:30:17
 */
@Repository
public interface GatewayApiMapper extends CommonMapper<GatewayApiDO> {
    /**
     * 查询分页信息
     * @param map
     * @return
     */
    List<GatewayApiDO> selectApiPage(Map<String, Object> map);
    
    /**
     * 模糊搜索数量处理
     * @param map
     * @return
     */
    Integer selectSearchCount(Map<String, Object> map);
    
    /**
     * 模糊搜索处理
     * @param map
     * @return
     */
    List<GatewayApiDO> selectSearchPage(Map<String, Object> map);

    Integer insertByMap(Map<String,Object> map);

    void insertByDO(GatewayApiDO gatewayApiDO);

    void deleteByIds(Map<String,Object> map);

    void updateByDto(GatewayApiDO gatewayApiDO);

    void updateTestTime(Long apiId);

    void updateDocumentTime(Long apiId);


    List<DocumentTimeCount> showDocumentTimeCount();

    List<InterfaceTimeCount> showInterfaceTimeCount();

    Integer selectAllInterface();

    Integer selectAllTest();

    Integer selectAllDocument();

    void insertMessage(GatewayMessageDO gatewayMessageDO);

    void updateMessage(GatewayMessageDO gatewayMessageDO);

    void deleteMessage(GatewayMessageDO gatewayMessageDO);

    List<GatewayMessageDO> selectMessage();
}