package com.avp.guo.entity;

import java.util.List;

/**
 * Created by guo on 2018/2/23.
 */
public class User {
    private Integer id;
    private String username;
    private List<UserPost> posts;

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

    public List<UserPost> getPosts() {
        return posts;
    }

    public void setPosts(List<UserPost> posts) {
        this.posts = posts;
    }
}
