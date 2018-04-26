package com.avp.mem.njpb.entity;

import org.hibernate.annotations.Where;

import javax.persistence.*;

/**
 * Created by six on 2017/7/24.
 */
@Entity
@Where(clause="remove_time is null")
@Table(name = "sys_user_detail", schema = "bussiness", catalog = "mem-pb-dev")
public class SysUserDetail {
    private Integer id;
    private Integer userId;
    private Integer status;

    @Id
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
