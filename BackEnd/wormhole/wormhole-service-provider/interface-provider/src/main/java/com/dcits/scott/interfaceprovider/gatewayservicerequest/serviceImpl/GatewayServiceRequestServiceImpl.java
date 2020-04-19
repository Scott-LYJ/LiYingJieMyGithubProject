package com.dcits.scott.interfaceprovider.gatewayservicerequest.serviceImpl;



import com.dcits.scott.gateway.pojo.GatewayServiceRequestDO;
import com.dcits.scott.interfaceprovider.gatewayservicerequest.mapper.GatewayServiceRequestMapper;
import com.dcits.scott.project.common.AbstractCommonServiceImpl;
import com.dcits.scott.project.common.CommonMapper;
import com.dcits.scott.project.gatewayservicerequest.GatewayServiceRequestService;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * API请求参数表ServiceImpl
 *
 */



@Service
public class GatewayServiceRequestServiceImpl extends AbstractCommonServiceImpl<GatewayServiceRequestDO> implements GatewayServiceRequestService {

  private static final Logger logger = LoggerFactory.getLogger(GatewayServiceRequestServiceImpl.class);

    @Resource
    private GatewayServiceRequestMapper gatewayservicerequestMapper;
    
//    @Autowired
//    private ZkService zkService;
    
    @Override
    protected CommonMapper<GatewayServiceRequestDO> getMapper() {
      return gatewayservicerequestMapper;
    }
    
    @Override
    public void delete(Long id) throws Exception {
      GatewayServiceRequestDO target = gatewayservicerequestMapper.selectByPrimaryKey(id);
      if(target == null) {
        return ;
      }
      int result = gatewayservicerequestMapper.deleteByPrimaryKey(id );
      if (result <= 0){
          logger.info("update ServiceRequest which id is {}, update nums is 0!",id);
//          throw DaoException.DB_DELETE_RESULT_0;
      }
      
      
      //查询输入参数
      Map<String, Object> map = new HashMap<String, Object>(2);
      map.put("apiId", target.getApiId());
      //已经按照index升序排序
      List<GatewayServiceRequestDO> paramList = gatewayservicerequestMapper.selectParamsList(map); 
      if(paramList.size() > 0) {
        for(int i = 0;i < paramList.size();i++) {
          GatewayServiceRequestDO param = paramList.get(i);
          //如果不是按照升序索引对应,则需要修改
          if(param.getParamsIndex() != i) {
            param.setParamsIndex(i);
            gatewayservicerequestMapper.updateByPrimaryKey(param);
          }
        }
      }
      
      // 全局通知
//      zkService.updateApi(target.getApiId());
      
    }

  @Override
  public void deleteLogic(Long id) throws Exception {

  }

  @Override
    public void insert(GatewayServiceRequestDO t) throws Exception {
      int result = gatewayservicerequestMapper.insertSelective(t);
      if (result <= 0){
//          throw DaoException.DB_UPDATE_RESULT_0;
      }

      // 全局通知
//      zkService.updateApi(t.getApiId());
    }

    @Override
    public void batchInsert(List<GatewayServiceRequestDO> list) throws Exception {
        this.gatewayservicerequestMapper.batchInsert(list);
    }

    @Override
    public void update(GatewayServiceRequestDO t) throws Exception {
      int result = gatewayservicerequestMapper.updateByPrimaryKeySelective(t);
      if (result <= 0){
          logger.info("update ServiceRequest which id is {}, update nums is 0!",t.getId());
//          throw DaoException.DB_DELETE_RESULT_0;
      }

      // 全局通知
//      zkService.updateApi(t.getApiId());
      
    }

  @Override
  public List<GatewayServiceRequestDO> selectByApiId(Long apiId) {
    return this.gatewayservicerequestMapper.selectByApiId(apiId);
  }

  @Override
  public void updateByPrimaryKeySelective(GatewayServiceRequestDO gatewayServiceRequestDO) {
    this.gatewayservicerequestMapper.updateByPrimaryKeySelective(gatewayServiceRequestDO);
  }
}




