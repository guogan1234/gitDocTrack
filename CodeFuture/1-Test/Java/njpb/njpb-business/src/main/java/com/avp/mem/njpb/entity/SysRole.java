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
@javax.persistence.Table(name = "sys_role", schema = "bussiness")
public class SysRole extends EntityBase {

    private String roleName;

    @Basic
    @javax.persistence.Column(name = "role_name")
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    private Integer groupId;

    @Basic
    @javax.persistence.Column(name = "group_id")
    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    private Integer roleGrade;

    @Basic
    @javax.persistence.Column(name = "role_grade")
    public Integer getRoleGrade() {
        return roleGrade;
    }

    public void setRoleGrade(Integer roleGrade) {
        this.roleGrade = roleGrade;
    }

    private String roleDescription;

    @Basic
    @javax.persistence.Column(name = "role_description")
    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    private String roleT2;

    @Basic
    @javax.persistence.Column(name = "role_t2")
    public String getRoleT2() {
        return roleT2;
    }

    public void setRoleT2(String roleT2) {
        this.roleT2 = roleT2;
    }

    private String roleT3;

    @Basic
    @javax.persistence.Column(name = "role_t3")
    public String getRoleT3() {
        return roleT3;
    }

    public void setRoleT3(String roleT3) {
        this.roleT3 = roleT3;
    }



}
