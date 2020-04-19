package com.dcits.scott.util;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 */

public class CacheUser implements Serializable {


    private static final long serialVersionUID = -3977272347775243323L;
    private String email;

    private String name;

    private Integer state;

    private String username;

    private String token;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "CacheUser{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", state=" + state +
                ", username='" + username + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
