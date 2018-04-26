/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.entity.system;

import com.avp.mem.njpb.api.entity.EntityBase;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.Where;

import javax.persistence.Basic;
import javax.persistence.Entity;

/**
 * Created by six on 2017/7/17.
 */
@Entity
@Where(clause = "remove_time is null")
@JsonInclude(JsonInclude.Include.NON_NULL)
@javax.persistence.Table(name = "sys_user_role", schema = "business")
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
