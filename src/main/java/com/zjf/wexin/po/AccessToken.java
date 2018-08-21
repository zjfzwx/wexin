package com.zjf.wexin.po;

public class AccessToken {
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    private String token;

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    private Integer expiresIn;
}
