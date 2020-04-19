package com.dcits.scott.apidocument;

import com.dcits.scott.apidocument.SubDocument.*;
import com.dcits.scott.common.CommonDO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * 接口文档实体类
 */
@Document("document")
public class ApiDocument extends CommonDO {
    @Id
    private String _id;
    private String sid;
    private ApiDetails apiDetails;
    private OtherFunction otherFunction;
    private List<RequestParam> requestParamList;
    private List<ResponseParam> responseParamList;
    private List<ResponseCode> responseCodeList;
    private String upContent;
    private String downContent;
    private List<Comment> comments;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getUpContent() {
        return upContent;
    }

    public void setUpContent(String upContent) {
        this.upContent = upContent;
    }

    public String getDownContent() {
        return downContent;
    }

    public void setDownContent(String downContent) {
        this.downContent = downContent;
    }

    public ApiDetails getApiDetails() {
        return apiDetails;
    }

    public void setApiDetails(ApiDetails apiDetails) {
        this.apiDetails = apiDetails;
    }

    public OtherFunction getOtherFunction() {
        return otherFunction;
    }

    public void setOtherFunction(OtherFunction otherFunction) {
        this.otherFunction = otherFunction;
    }

    public List<RequestParam> getRequestParamList() {
        return requestParamList;
    }

    public void setRequestParamList(List<RequestParam> requestParamList) {
        this.requestParamList = requestParamList;
    }

    public List<ResponseParam> getResponseParamList() {
        return responseParamList;
    }

    public void setResponseParamList(List<ResponseParam> responseParamList) {
        this.responseParamList = responseParamList;
    }

    public List<ResponseCode> getResponseCodeList() {
        return responseCodeList;
    }

    public void setResponseCodeList(List<ResponseCode> responseCodeList) {
        this.responseCodeList = responseCodeList;
    }
}
