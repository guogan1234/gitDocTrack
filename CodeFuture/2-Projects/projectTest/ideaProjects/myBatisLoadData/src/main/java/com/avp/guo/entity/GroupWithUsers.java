package com.avp.guo.entity;

import java.util.List;

/**
 * Created by guo on 2018/2/23.
 */
public class GroupWithUsers {
    private Integer id;
    private String groupname;
    private List<UserForMany> userList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public List<UserForMany> getUserList() {
        return userList;
    }

    public void setUserList(List<UserForMany> userList) {
        this.userList = userList;
    }
}
