package com.dcits.scott.admin.pojo;

import com.dcits.scott.admin.pojo.common.CommonDO;
import com.dcits.scott.admin.pojo.common.ProtoBean;

import java.util.Date;

public class AuthPermission extends ProtoBean {
    private Integer id;

    private Integer resId;

    private Integer roleId;

    private Date cdt;

    private Date udt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getResId() {
        return resId;
    }

    public void setResId(Integer resId) {
        this.resId = resId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
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