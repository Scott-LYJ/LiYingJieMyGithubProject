package com.dcits.scott.support.entity;

import com.dcits.scott.common.CommonDO;
import com.dcits.scott.tool.DateUtil;

import java.util.Date;

/**
 * 接口创建时间实体类
 */
public class InterfaceTimeCount extends CommonDO {
    Date interfaceTime;
    Integer count;


    public String getInterfaceTime() {
        return DateUtil.getFormatTime(interfaceTime);
    }

    public void setInterfaceTime(Date interfaceTime) {
        this.interfaceTime = interfaceTime;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
