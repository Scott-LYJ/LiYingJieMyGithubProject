package com.dcits.scott.admin.pojo;

import com.dcits.scott.common.CommonDO;
import com.dcits.scott.common.ProtoBean;

import java.util.Date;

/**
 * 角色类
 */
public class AuthRole extends ProtoBean {
    private Integer id;

    private String name;

    private Date cdt;

    private Date udt;

    private String des;

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

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