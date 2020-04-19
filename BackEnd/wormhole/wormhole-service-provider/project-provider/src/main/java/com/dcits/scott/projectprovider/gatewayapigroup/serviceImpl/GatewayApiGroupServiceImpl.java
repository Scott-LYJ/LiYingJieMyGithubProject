package com.dcits.scott.projectprovider.gatewayapigroup.serviceImpl;

import com.dcits.scott.gateway.pojo.GatewayApiDO;
import com.dcits.scott.gateway.pojo.GatewayApiGroupDO;
import com.dcits.scott.project.common.AbstractCommonServiceImpl;
import com.dcits.scott.project.common.CommonMapper;
import com.dcits.scott.project.gatewayapigroup.GatewayApiGroupService;
import com.dcits.scott.projectprovider.gatewayapigroup.mapper.GatewayApiGroupMapper;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class GatewayApiGroupServiceImpl extends AbstractCommonServiceImpl<GatewayApiGroupDO> implements GatewayApiGroupService {

    @Resource
    GatewayApiGroupMapper gatewayApiGroupMapper;
    @Override
    protected CommonMapper<GatewayApiGroupDO> getMapper() {
        return gatewayApiGroupMapper;
    }

    @Override
    public void insert(GatewayApiGroupDO gatewayApiGroupDO) throws Exception {

    }


    @Override
    public void batchInsert(List<GatewayApiGroupDO> list) throws Exception {

    }

    @Override
    public void delete(Long id) throws Exception {

    }

    @Override
    public void deleteLogic(Long id) throws Exception {

    }

    @Override
    public void update(GatewayApiGroupDO gatewayApiGroupDO) throws Exception {

    }

    @Override
    public List<GatewayApiGroupDO> selectPage(Map<String, Object> map) throws Exception {
        return gatewayApiGroupMapper.selectPage(map);
    }

    @Override
    public void insertByMap(Map<String, Object> map) {
        this.gatewayApiGroupMapper.insertByMap(map);
    }

    @Override
    public void delSysGroupByIds(Map<String, Object> map) {
        this.gatewayApiGroupMapper.delSysGroupByIds(map);
    }

    @Override
    public void updateGroupByMap(Map<String, Object> map) {
        this.gatewayApiGroupMapper.updateGroupByMap(map);
    }

    @Override
    public GatewayApiGroupDO selectId(Long id) {
        return this.gatewayApiGroupMapper.selectId(id);
    }
}
