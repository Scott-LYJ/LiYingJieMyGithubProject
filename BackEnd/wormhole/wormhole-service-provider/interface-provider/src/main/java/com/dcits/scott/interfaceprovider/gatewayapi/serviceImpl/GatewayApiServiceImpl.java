package com.dcits.scott.interfaceprovider.gatewayapi.serviceImpl;


import com.dcits.scott.gateway.pojo.GatewayApiDO;
import com.dcits.scott.gateway.pojo.GatewayMessageDO;
import com.dcits.scott.interfaceprovider.gatewayapi.mapper.GatewayApiMapper;
import com.dcits.scott.project.common.AbstractCommonServiceImpl;
import com.dcits.scott.project.common.CommonMapper;
import com.dcits.scott.project.gatewayapi.GatewayApiService;
import com.dcits.scott.projectprovider.gatewayapigroup.mapper.GatewayApiGroupMapper;
import com.dcits.scott.support.entity.DocumentTimeCount;
import com.dcits.scott.support.entity.InterfaceTimeCount;
import com.dcits.scott.tool.ZkService;
import com.google.common.collect.Maps;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * API接口表ServiceImpl

 */



@Service
public class GatewayApiServiceImpl extends AbstractCommonServiceImpl<GatewayApiDO> implements GatewayApiService {
    private static final Logger logger = LoggerFactory.getLogger(GatewayApiServiceImpl.class);
    
    @Resource
    private GatewayApiMapper gatewayApiMapper;


    @Override
    protected CommonMapper<GatewayApiDO> getMapper() {
        return this.gatewayApiMapper;
    }

    @Override
    public Integer selectSearchCount(Map<String, Object> map) {
        return this.gatewayApiMapper.selectSearchCount(map);
    }

    @Override
    public List<GatewayApiDO> selectSearchPage(Map<String, Object> map) {
        return this.gatewayApiMapper.selectSearchPage(map);
    }

    @Override
    public Integer insertByMap(Map<String, Object> map) {
        return this.gatewayApiMapper.insertByMap(map);
    }

    @Override
    public GatewayApiDO insertByDO(GatewayApiDO gatewayApiDO) {
        this.gatewayApiMapper.insertByDO(gatewayApiDO);
        return gatewayApiDO;
    }

    @Override
    public void deleteByIds(Map<String,Object> map) {
        this.gatewayApiMapper.deleteByIds(map);
    }

    @Override
    public void updateTestTime(Long apiId) {
        this.gatewayApiMapper.updateTestTime(apiId);
    }

    @Override
    public void updateDocumentTime(Long apiId) {
        this.gatewayApiMapper.updateDocumentTime(apiId);
    }



    @Override
    public List<DocumentTimeCount> showDocumentTimeCount() {
        return this.gatewayApiMapper.showDocumentTimeCount();
    }

    @Override
    public List<InterfaceTimeCount> showInterfaceTimeCount() {
        return this.gatewayApiMapper.showInterfaceTimeCount();
    }

    @Override
    public Integer selectAllInterface() {
        return this.gatewayApiMapper.selectAllInterface();
    }

    @Override
    public Integer selectAllTest() {
        return this.gatewayApiMapper.selectAllTest();
    }

    @Override
    public Integer selectAllDocument() {
        return this.gatewayApiMapper.selectAllDocument();
    }

    @Override
    public void insertMessage(GatewayMessageDO gatewayMessageDO) {
        this.gatewayApiMapper.insertMessage(gatewayMessageDO);
    }

    @Override
    public void updateMessage(GatewayMessageDO gatewayMessageDO) {
        this.gatewayApiMapper.updateMessage(gatewayMessageDO);
    }

    @Override
    public void deleteMessage(GatewayMessageDO gatewayMessageDO) {
        this.gatewayApiMapper.deleteMessage(gatewayMessageDO);
    }

    @Override
    public List<GatewayMessageDO> selectMessage() {
        return gatewayApiMapper.selectMessage();
    }


    @Override
    public void insert(GatewayApiDO gatewayApiDO) throws Exception {
        this.gatewayApiMapper.insert(gatewayApiDO);
    }

    @Override
    public void batchInsert(List<GatewayApiDO> list) throws Exception {

    }

    @Override
    public void delete(Long id) throws Exception {

    }

    @Override
    public void deleteLogic(Long id) throws Exception {

    }

    @Override
    public void update(GatewayApiDO gatewayApiDO) throws Exception {
        this.gatewayApiMapper.updateByDto(gatewayApiDO);
    }
}




