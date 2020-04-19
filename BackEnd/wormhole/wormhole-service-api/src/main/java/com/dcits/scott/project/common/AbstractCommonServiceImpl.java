/*
 * Copyright 2016 kaistart.com All right reserved. This software is the
 * confidential and proprietary information of kaistart.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with kaistart.com.
 */
package com.dcits.scott.project.common;

import com.dcits.scott.common.CommonDO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public abstract class AbstractCommonServiceImpl<T extends CommonDO> {
  /**
   * 获取mapper对象
   * @return
   */
  protected abstract CommonMapper<T> getMapper();

  public T selectById(Long id) throws Exception {
    return (T) getMapper().selectByPrimaryKey(id);
  }

  /**
   * 根据查询条件返回查询结果记录数。一般用于分页查询。
   *
   * @param map 查询条件映射
   * @throws Exception 数据库访问异常
   */

  public Integer selectCount(Map<String, Object> map) throws Exception {
    return getMapper().selectCount(map);
  }

  /**
   * 根据查询条件返回查询结果。
   *
   * @param map 查询条件映射
   * @throws Exception 数据库访问异常
   */

  public List<T> selectList(Map<String, Object> map) throws Exception {
    return getMapper().selectList(map);
  }
  
  /**
   * 根据查询条件分页查询。
   *
   * @throws Exception 数据库访问异常
   */


  public List<T> selectPage(Map<String, Object> map) throws Exception {
    return getMapper().selectPage(map);
  }
  

}
