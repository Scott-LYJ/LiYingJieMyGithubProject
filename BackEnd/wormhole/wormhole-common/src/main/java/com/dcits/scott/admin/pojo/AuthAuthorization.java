package com.dcits.scott.admin.pojo;

import com.dcits.scott.common.CommonDO;
import com.dcits.scott.common.ProtoBean;

import java.util.Date;

/**
 * 角色-用户关系类
 */
public class AuthAuthorization extends ProtoBean {
    private Integer id;

    private Integer userId;

    private Integer roleId;

    private Date cdt;

    private Date udt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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