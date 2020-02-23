package com.example.xiaomi.model;

import org.litepal.crud.DataSupport;

public class UserInfo extends DataSupport {
    private String name;
    private String saying;
    private  byte[] profile;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSaying() {
        return saying;
    }

    public void setSaying(String saying) {
        this.saying = saying;
    }

    public byte[] getProfile() {
        return profile;
    }

    public void setProfile(byte[] profile) {
        this.profile = profile;
    }
}
