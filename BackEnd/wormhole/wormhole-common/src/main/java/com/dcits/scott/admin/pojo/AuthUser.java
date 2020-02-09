package com.dcits.scott.admin.pojo;

import com.dcits.scott.admin.pojo.common.CommonDO;
import com.dcits.scott.admin.pojo.common.ProtoBean;

import java.util.Date;

public class AuthUser extends ProtoBean {
    private static final long serialVersionUID = 1L;
    private Integer id;

    private String name;

    private String password;

    private Date cdt;

    private Date udt;

    private String avatar;

    private String address;

    private String phone;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    @Override
    public String toString() {
        return "AuthUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", cdt=" + cdt +
                ", udt=" + udt +
                ", avatar='" + avatar + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}