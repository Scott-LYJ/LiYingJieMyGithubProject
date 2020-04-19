package com.dcits.scott.gateway.pojo;

import com.dcits.scott.common.CommonDO;
import com.dcits.scott.tool.DateUtil;
import org.apache.http.client.utils.DateUtils;

import java.util.Date;

/**
 * 消息DO
 */
public class GatewayMessageDO extends CommonDO {

    private Long id;
    private Integer userId;
    private Integer roleId;
    private Date sendDate;
    private String message;
    private Integer isRead;
    private String userName;
    private String roleName;
    private String color;
    private String icon;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getSendDate() {
        return DateUtil.getFormatDate(sendDate);
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }
}
