package com.dcits.scott.support.entity;

import com.dcits.scott.common.CommonDO;
import com.dcits.scott.tool.DateUtil;

import java.util.Date;

public class DocumentTimeCount extends CommonDO {
    Date documentTime;
    Integer count;

    public String getDocumentTime() {
        return DateUtil.getFormatTime(documentTime);
    }

    public void setDocumentTime(Date documentTime) {
        this.documentTime = documentTime;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
