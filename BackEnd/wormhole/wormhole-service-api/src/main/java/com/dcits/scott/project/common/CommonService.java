package com.dcits.scott.project.common;

import com.dcits.scott.common.CommonDO;
import com.dcits.scott.gateway.pojo.GatewayProjectDO;


import java.util.List;
import java.util.Map;

/**
 *
 * 公共service接口层
 */
public interface CommonService<T extends CommonDO> {

  /**
   * 公共插入方法
   * @param t 自定义类型
   * @throws Exception
   */
  void insert(T t) throws Exception;
  
  /**
   * 公共批量插入方法
   * @param list 自定义类型集合
   * @throws Exception
   */
  void batchInsert(List<T> list) throws Exception;
  
  /**
   * 公共删除方法,物理删除
   * @param id 主键ID
   * @throws Exception
   */
  void delete(Long id) throws Exception;
  
  /**
   * 公共删除方法，逻辑删除,只改变数据状态
   * @param id 主键ID
   * @throws Exception
   */
  void deleteLogic(Long id) throws Exception;
  
  /**
   * 公共更新方法
   * @param t 自定义类型
   * @throws Exception
   */
  void update(T t) throws Exception;
  
  /**
   * 公共根据主键查询数据
   * @param id 主键ID
   * @return 自定义类型
   * @throws Exception
   */
  T selectById(Long id) throws Exception;
  
  /**
   * 公共查询总数方法
   * @param map 查询条件
   * @return
   * @throws Exception
   */
  Integer selectCount(Map<String, Object> map) throws Exception;

  /**
   * 公共查询集合方法
   * @param map 查询条件
   * @return
   * @throws Exception
   */
  List<T> selectList(Map<String, Object> map) throws Exception;


  /**
   * 分页方法
   * @param map
   * @return
   * @throws Exception
   */
  List<T> selectPage(Map<String, Object> map) throws Exception ;

}
