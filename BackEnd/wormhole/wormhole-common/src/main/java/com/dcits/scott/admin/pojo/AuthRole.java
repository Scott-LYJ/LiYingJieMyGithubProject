package com.dcits.scott.admin.pojo;

import com.dcits.scott.admin.pojo.common.CommonDO;
import com.dcits.scott.admin.pojo.common.ProtoBean;

import java.util.Date;

public class AuthRole extends ProtoBean {
    private Integer id;

    private String name;

    private Date cdt;

    private Date udt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Date getCdt() {
        return cdt;
    }

    public void setCdt(Date cdt) {
        this.cdt = cdt;
    }

    public Date getUdt() {
        return udt;
    }

    public void setUdt(Date udt) {
        this.udt = udt;
    }
}