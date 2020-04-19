package com.dcits.scott.projectprovider.gatewayproject.serviceImpl;

import com.dcits.scott.gateway.pojo.GatewayProjectDO;
import com.dcits.scott.project.common.AbstractCommonServiceImpl;
import com.dcits.scott.project.common.CommonMapper;
import com.dcits.scott.project.gatewayproject.GatewayProjectService;
import com.dcits.scott.projectprovider.gatewayproject.mapper.GatewayProjectMapper;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class GatewayProjectServiceImpl extends AbstractCommonServiceImpl<GatewayProjectDO> implements GatewayProjectService{

    @Resource
    GatewayProjectMapper gatewayProjectMapper;


    @Override
    public String hello() {
        return null;
    }

    @Override
    public List<GatewayProjectDO> queryProjectList(Map<String, Object> map) {
        return gatewayProjectMapper.queryProjectList(map);
    }

    @Override
    public void deleteBatch(Map<String, Object> map) {
        this.gatewayProjectMapper.deleteBatch(map);
    }

    @Override
    public void insert(Map<String, Object> map) {
        this.gatewayProjectMapper.insert(map);
    }

    @Override
    public void updateByMap(Map<String, Object> map) {
        this.gatewayProjectMapper.updateByMap(map);
    }

    @Override
    public void deleteByPrimaryKey(Integer id) {
        this.gatewayProjectMapper.deleteByPrimaryKey(id);
    }

    @Override
    public GatewayProjectDO selectByIntId(int id) {
        return this.gatewayProjectMapper.selectByPrimaryKey(id);
    }

    @Override
    protected CommonMapper<GatewayProjectDO> getMapper() {
        return gatewayProjectMapper;
    }


    @Override
    public void insert(GatewayProjectDO gatewayProjectDO) throws Exception {

    }

    @Override
    public void batchInsert(List<GatewayProjectDO> list) throws Exception {

    }

    @Override
    public void delete(Long id) throws Exception {

    }

    @Override
    public void deleteLogic(Long id) throws Exception {

    }

    @Override
    public void update(GatewayProjectDO gatewayProjectDO) throws Exception {

    }
}
