package com.dcits.scott.project.gatewayapi;

import com.dcits.scott.gateway.pojo.GatewayApiDO;
import com.dcits.scott.gateway.pojo.GatewayMessageDO;
import com.dcits.scott.project.common.CommonService;
import com.dcits.scott.support.entity.DocumentTimeCount;
import com.dcits.scott.support.entity.InterfaceTimeCount;


import java.util.List;
import java.util.Map;

/**
 * 
 * API接口表 接口
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
public interface GatewayApiService extends CommonService<GatewayApiDO> {

  /**
   * 获取搜索总数
   * @param map
   * @return
   */
  Integer selectSearchCount(Map<String, Object> map);
  
  /**
   * 获取搜索集合信息
   * @param map
   * @return
   */
  List<GatewayApiDO> selectSearchPage(Map<String, Object> map);


  Integer insertByMap(Map<String,Object> map);

  GatewayApiDO insertByDO(GatewayApiDO gatewayApiDO);

    void deleteByIds(Map<String,Object> map);

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


//  /**
//   * 同步数据
//   * @param bean
//   * @return
//   */
//  boolean transferData(GatewayApiRequestDO bean);
//
//  /**
//   * 拷贝数据
//   * @param bean
//   * @return
//   */
//  boolean copyData(GatewayApiRequestDO bean);
}