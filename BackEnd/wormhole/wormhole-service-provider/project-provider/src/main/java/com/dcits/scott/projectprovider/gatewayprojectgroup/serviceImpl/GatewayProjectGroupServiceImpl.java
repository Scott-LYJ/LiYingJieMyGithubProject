package com.dcits.scott.projectprovider.gatewayprojectgroup.serviceImpl;

import com.dcits.scott.gateway.pojo.GatewayApiGroupDO;
import com.dcits.scott.gateway.pojo.GatewayProjectGroupDO;
import com.dcits.scott.project.common.AbstractCommonServiceImpl;
import com.dcits.scott.project.common.CommonMapper;
import com.dcits.scott.project.gatewayprojectgroup.GatewayProjectGroupService;
import com.dcits.scott.projectprovider.gatewayprojectgroup.mapper.GatewayProjectGroupMapper;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
@Service
public class GatewayProjectGroupServiceImpl extends AbstractCommonServiceImpl<GatewayProjectGroupDO>  implements GatewayProjectGroupService{


    @Resource
    GatewayProjectGroupMapper gatewayProjectGroupMapper;


    @Override
    public void insert(GatewayProjectGroupDO gatewayProjectGroupDO) throws Exception {

    }

    @Override
    public void batchInsert(List<GatewayProjectGroupDO> list) throws Exception {

    }

    @Override
    public void delete(Long id) throws Exception {

    }

    @Override
    public void deleteLogic(Long id) throws Exception {

    }

    @Override
    public void update(GatewayProjectGroupDO gatewayProjectGroupDO) throws Exception {

    }


    @Override
    protected CommonMapper<GatewayProjectGroupDO> getMapper() {
        return gatewayProjectGroupMapper;
    }


    @Override
    public Integer selectCount(Map<String, Object> map) throws Exception {
        return gatewayProjectGroupMapper.selectCount(map);
    }

    @Override
    public List<GatewayProjectGroupDO> selectList(Map<String, Object> map) throws Exception {
        return getMapper().selectList(map);
    }

    @Override
    public List<GatewayProjectGroupDO> selectByProjectId(Integer projectId) {
        return this.gatewayProjectGroupMapper.selectByProjectId(projectId);
    }

    @Override
    public void insertByProGou(Map<String, Object> map) {
        this.gatewayProjectGroupMapper.insertByProGou(map);
    }

    @Override
    public void deleteByProGou(Map<String, Object> map) {
        this.gatewayProjectGroupMapper.deleteByProGou(map);
    }

    @Override
    public void deleteByProApi(Map<String, Object> map) {
        this.gatewayProjectGroupMapper.deleteByProApi(map);
    }

    @Override
    public void insertByProApi(Map<String, Object> map) {
        this.gatewayProjectGroupMapper.insertByProApi(map);
    }

    @Override
    public Integer selectCount2(Map<String, Object> map) {
        return this.gatewayProjectGroupMapper.selectCount2(map);
    }
}
