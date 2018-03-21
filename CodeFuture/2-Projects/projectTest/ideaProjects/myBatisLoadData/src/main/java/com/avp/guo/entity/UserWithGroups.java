package com.avp.guo.entity;

import java.util.List;

/**
 * Created by guo on 2018/2/23.
 */
public class UserWithGroups {
    private Integer id;
    private String username;
    private List<GroupForMany> groupList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<GroupForMany> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<GroupForMany> groupList) {
        this.groupList = groupList;
    }
}
