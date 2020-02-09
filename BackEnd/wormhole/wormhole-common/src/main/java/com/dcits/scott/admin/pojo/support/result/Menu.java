package com.dcits.scott.admin.pojo.support.result;

import com.dcits.scott.admin.pojo.AuthResource;

import java.util.List;

public class Menu extends AuthResource {

    // 子菜单
    private List<Menu> sysMenuVoChild;



    public List<Menu> getSysMenuVoChild() {
        return sysMenuVoChild;
    }

    public void setSysMenuVoChild(List<Menu> sysMenuVoChild) {
        this.sysMenuVoChild = sysMenuVoChild;
    }


}
