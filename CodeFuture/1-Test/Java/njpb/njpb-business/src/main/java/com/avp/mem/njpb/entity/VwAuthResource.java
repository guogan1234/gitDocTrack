package com.avp.mem.njpb.entity;

import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by six on 2017/7/25.
 */
@Entity
@Where(clause="remove_time is null")
@Table(name = "vw_auth_resource", schema = "bussiness", catalog = "mem-pb-dev")
public class VwAuthResource {

    @Id
    private Integer id;
    private String resourceCode;
    private Integer resourceTypeId;
    private Integer userId;
    private String userAccount;
    private Timestamp createTime;
    private Integer createBy;
    private Timestamp removeTime;

    @Basic
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "resource_code")
    public String getResourceCode() {
        return resourceCode;
    }

    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode;
    }

    @Basic
    @Column(name = "resource_type_id")
    public Integer getResourceTypeId() {
        return resourceTypeId;
    }

    public void setResourceTypeId(Integer resourceTypeId) {
        this.resourceTypeId = resourceTypeId;
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
    @Column(name = "user_account")
    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    @Basic
    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "create_by")
    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    @Basic
    @Column(name = "remove_time")
    public Timestamp getRemoveTime() {
        return removeTime;
    }

    public void setRemoveTime(Timestamp removeTime) {
        this.removeTime = removeTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VwAuthResource that = (VwAuthResource) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (resourceCode != null ? !resourceCode.equals(that.resourceCode) : that.resourceCode != null) return false;
        if (resourceTypeId != null ? !resourceTypeId.equals(that.resourceTypeId) : that.resourceTypeId != null)
            return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (userAccount != null ? !userAccount.equals(that.userAccount) : that.userAccount != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (createBy != null ? !createBy.equals(that.createBy) : that.createBy != null) return false;
        if (removeTime != null ? !removeTime.equals(that.removeTime) : that.removeTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (resourceCode != null ? resourceCode.hashCode() : 0);
        result = 31 * result + (resourceTypeId != null ? resourceTypeId.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (userAccount != null ? userAccount.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (createBy != null ? createBy.hashCode() : 0);
        result = 31 * result + (removeTime != null ? removeTime.hashCode() : 0);
        return result;
    }
}
