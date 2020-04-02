package com.dcits.scott.support.entity;

import com.dcits.scott.common.CommonDO;

import java.util.List;

public class Echarts extends CommonDO {
    Integer allInterfaceCount;
    Integer allTestCount;
    Integer allDocumentCount;
    List<String> time;
    List<Integer> documentCount;
    List<Integer> interfaceCount;

    public Integer getAllInterfaceCount() {
        return allInterfaceCount;
    }

    public void setAllInterfaceCount(Integer allInterfaceCount) {
        this.allInterfaceCount = allInterfaceCount;
    }

    public Integer getAllTestCount() {
        return allTestCount;
    }

    public void setAllTestCount(Integer allTestCount) {
        this.allTestCount = allTestCount;
    }

    public Integer getAllDocumentCount() {
        return allDocumentCount;
    }

    public void setAllDocumentCount(Integer allDocumentCount) {
        this.allDocumentCount = allDocumentCount;
    }

    public List<String> getTime() {
        return time;
    }

    public void setTime(List<String> time) {
        this.time = time;
    }

    public List<Integer> getDocumentCount() {
        return documentCount;
    }

    public void setDocumentCount(List<Integer> documentCount) {
        this.documentCount = documentCount;
    }

    public List<Integer> getInterfaceCount() {
        return interfaceCount;
    }

    public void setInterfaceCount(List<Integer> interfaceCount) {
        this.interfaceCount = interfaceCount;
    }
}
