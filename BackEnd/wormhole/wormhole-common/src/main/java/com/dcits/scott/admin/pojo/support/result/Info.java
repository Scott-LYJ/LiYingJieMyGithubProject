package com.dcits.scott.admin.pojo.support.result;

import com.dcits.scott.admin.pojo.AuthResource;
import com.dcits.scott.admin.pojo.AuthRole;
import com.dcits.scott.admin.pojo.AuthUser;

import java.util.List;

public class Info {
    private AuthUser userInfo;
    private List<AuthRole> sysRoleVoList;
    private List<AuthResource> sysMenuVoList;


    public Info(AuthUser userInfo, List<AuthRole> sysRoleVoList, List<AuthResource> sysMenuVoList) {
        this.userInfo = userInfo;
        this.sysRoleVoList = sysRoleVoList;
        this.sysMenuVoList = sysMenuVoList;
    }

    @Override
    public String toString() {
        return "Info{" +
                "userInfo=" + userInfo +
                ", sysRoleVoList=" + sysRoleVoList +
                ", sysMenuVoList=" + sysMenuVoList +
                '}';
    }

    public AuthUser getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(AuthUser userInfo) {
        this.userInfo = userInfo;
    }

    public List<AuthRole> getSysRoleVoList() {
        return sysRoleVoList;
    }

    public void setSysRoleVoList(List<AuthRole> sysRoleVoList) {
        this.sysRoleVoList = sysRoleVoList;
    }

    public List<AuthResource> getSysMenuVoList() {
        return sysMenuVoList;
    }

    public void setSysMenuVoList(List<AuthResource> sysMenuVoList) {
        this.sysMenuVoList = sysMenuVoList;
    }
}
