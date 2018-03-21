package com.avp.guo.entity;

import java.io.Serializable;

/**
 * Created by guo on 2018/3/7.
 */
public class User implements Serializable{
    private String name;
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
