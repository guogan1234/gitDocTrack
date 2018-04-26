package com.avp.mem.njpb.entity;

import com.avp.mem.njpb.api.entity.EntityBase;
import org.hibernate.annotations.Where;

import javax.persistence.Basic;
import javax.persistence.Entity;

/**
 * Created by six on 2017/7/17.
 */
@Entity
@Where(clause="remove_time is null")
@javax.persistence.Table(name = "sys_user_role", schema = "bussiness")
public class SysUserRole extends EntityBase {
    private Integer roleId;

    public SysUserRole() {
    }

    public SysUserRole(Integer userId, Integer roleId) {
        this.roleId = roleId;
        this.userId = userId;
    }


    @Basic
    @javax.persistence.Column(name = "role_id")
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    private Integer userId;

    @Basic
    @javax.persistence.Column(name = "user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }




}
