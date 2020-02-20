package com.dcits.scott.support.result;

import java.util.List;
import java.util.Map;

public class ResultInfo<T> {
    private String status;
    private String message;
    private List<T> dataList;
    private Map<String,Info> data;
    private Integer count;
    private String success;

    public ResultInfo() {
    }

    public ResultInfo(String status, String message, List<T> dataList, Map<String, Info> data, Integer count, String success) {
        this.status = status;
        this.message = message;
        this.dataList = dataList;
        this.data = data;
        this.count = count;
        this.success = success;
    }

    @Override
    public String toString() {
        return "ResultInfo{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", dataList=" + dataList +
                ", data=" + data +
                ", count='" + count + '\'' +
                ", success='" + success + '\'' +
                '}';
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public Map<String, Info> getData() {
        return data;
    }

    public void setData(Map<String, Info> data) {
        this.data = data;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
