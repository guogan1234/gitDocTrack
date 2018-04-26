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
@javax.persistence.Table(name = "sys_role_resource", schema = "bussiness")
public class SysRoleResource extends EntityBase {
    private Integer resourceId;

    @Basic
    @javax.persistence.Column(name = "resource_id")
    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    private Integer roleId;

    @Basic
    @javax.persistence.Column(name = "role_id")
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

}
