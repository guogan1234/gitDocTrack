package com.avp.mems.backstage.entity.push;

import javax.persistence.*;

/**
 * Created by len on 2017/6/2.
 */
@Entity
@Table(name = "push_info_wechat")
public class PushInfoWechat{
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
}
