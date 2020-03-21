package com.dcits.scott.apidocument.SubDocument;

import com.dcits.scott.common.CommonDO;

public class ResponseCode extends CommonDO {
    private Integer code;
    private String description;
    private String reason;
    private String solution;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }
}
