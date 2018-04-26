package com.avp.mems.push.entities;

import javax.persistence.*;

/**
 * Created by len on 2017/6/2.
 */
@Entity
@Table(name = "push_info_wechat")
public class PushInfoWechat {

    private String username;
    private String wechatid;

    @Id
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "wechatid")
    public String getWechatid() {
        return wechatid;
    }

    public void setWechatid(String wechatid) {
        this.wechatid = wechatid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PushInfoWechat that = (PushInfoWechat) o;

        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (wechatid != null ? !wechatid.equals(that.wechatid) : that.wechatid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (wechatid != null ? wechatid.hashCode() : 0);
        return result;
    }
}
