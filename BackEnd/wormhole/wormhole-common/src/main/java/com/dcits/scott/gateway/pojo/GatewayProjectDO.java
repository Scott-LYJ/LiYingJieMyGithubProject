package com.dcits.scott.gateway.pojo;

import com.dcits.scott.admin.pojo.AuthUser;
import com.dcits.scott.common.CommonDO;
import com.dcits.scott.tool.DateUtil;

import java.util.Date;

/**
 * 项目实体类
 */
public class GatewayProjectDO extends CommonDO {
    private Integer id;

    private String name;

    private String des;

    private String avatar;

    private Date cdt;

    private Integer createrid;

    private AuthUser authUser;

    public AuthUser getAuthUser() {
        return authUser;
    }

    public void setAuthUser(AuthUser authUser) {
        this.authUser = authUser;
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

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des == null ? null : des.trim();
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    public String getCdt() {
        return DateUtil.getFormatTime(cdt);
    }
    public void setCdt(Date cdt) {
        this.cdt = cdt;
    }

    public Integer getCreaterid() {
        return createrid;
    }

    public void setCreaterid(Integer createrid) {
        this.createrid = createrid;
    }
}