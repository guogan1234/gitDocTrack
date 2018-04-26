package com.avp.mem.njpb.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by six on 2017/7/25.
 */
@Entity
@Table(name = "vw_user", schema = "bussiness", catalog = "mem-pb-dev")
public class VwUser {

    private String roleIds;
    @Id
    private Integer id;
    private String userAccount;
    private String userName;
    private String userPassword;
    private String userSurname;
    private Integer userStatus;
    private Integer userType;
    private Integer corpId;
    private String userPhone;
    private Integer whitelist;
    private Integer userGroup;
    private Timestamp createTime;
    private Integer createBy;
    private Timestamp lastUpdateTime;
    private Integer lastUpdateBy;
    private Timestamp removeTime;
    private String userEmail;
    private String userQq;
    private String userWechart;

    @Basic
    @Column(name = "role_ids")
    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    @Basic
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
    @Column(name = "user_name")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "user_password")
    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    @Basic
    @Column(name = "user_surname")
    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    @Basic
    @Column(name = "user_status")
    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    @Basic
    @Column(name = "user_type")
    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    @Basic
    @Column(name = "corp_id")
    public Integer getCorpId() {
        return corpId;
    }

    public void setCorpId(Integer corpId) {
        this.corpId = corpId;
    }

    @Basic
    @Column(name = "user_phone")
    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    @Basic
    @Column(name = "whitelist")
    public Integer getWhitelist() {
        return whitelist;
    }

    public void setWhitelist(Integer whitelist) {
        this.whitelist = whitelist;
    }

    @Basic
    @Column(name = "user_group")
    public Integer getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(Integer userGroup) {
        this.userGroup = userGroup;
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
    @Column(name = "last_update_time")
    public Timestamp getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Timestamp lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    @Basic
    @Column(name = "last_update_by")
    public Integer getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(Integer lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    @Basic
    @Column(name = "remove_time")
    public Timestamp getRemoveTime() {
        return removeTime;
    }

    public void setRemoveTime(Timestamp removeTime) {
        this.removeTime = removeTime;
    }

    @Basic
    @Column(name = "user_email")
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Basic
    @Column(name = "user_qq")
    public String getUserQq() {
        return userQq;
    }

    public void setUserQq(String userQq) {
        this.userQq = userQq;
    }

    @Basic
    @Column(name = "user_wechart")
    public String getUserWechart() {
        return userWechart;
    }

    public void setUserWechart(String userWechart) {
        this.userWechart = userWechart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VwUser vwUser = (VwUser) o;

        if (roleIds != null ? !roleIds.equals(vwUser.roleIds) : vwUser.roleIds != null) return false;
        if (id != null ? !id.equals(vwUser.id) : vwUser.id != null) return false;
        if (userAccount != null ? !userAccount.equals(vwUser.userAccount) : vwUser.userAccount != null) return false;
        if (userName != null ? !userName.equals(vwUser.userName) : vwUser.userName != null) return false;
        if (userPassword != null ? !userPassword.equals(vwUser.userPassword) : vwUser.userPassword != null)
            return false;
        if (userSurname != null ? !userSurname.equals(vwUser.userSurname) : vwUser.userSurname != null) return false;
        if (userStatus != null ? !userStatus.equals(vwUser.userStatus) : vwUser.userStatus != null) return false;
        if (userType != null ? !userType.equals(vwUser.userType) : vwUser.userType != null) return false;
        if (corpId != null ? !corpId.equals(vwUser.corpId) : vwUser.corpId != null) return false;
        if (userPhone != null ? !userPhone.equals(vwUser.userPhone) : vwUser.userPhone != null) return false;
        if (whitelist != null ? !whitelist.equals(vwUser.whitelist) : vwUser.whitelist != null) return false;
        if (userGroup != null ? !userGroup.equals(vwUser.userGroup) : vwUser.userGroup != null) return false;
        if (createTime != null ? !createTime.equals(vwUser.createTime) : vwUser.createTime != null) return false;
        if (createBy != null ? !createBy.equals(vwUser.createBy) : vwUser.createBy != null) return false;
        if (lastUpdateTime != null ? !lastUpdateTime.equals(vwUser.lastUpdateTime) : vwUser.lastUpdateTime != null)
            return false;
        if (lastUpdateBy != null ? !lastUpdateBy.equals(vwUser.lastUpdateBy) : vwUser.lastUpdateBy != null)
            return false;
        if (removeTime != null ? !removeTime.equals(vwUser.removeTime) : vwUser.removeTime != null) return false;
        if (userEmail != null ? !userEmail.equals(vwUser.userEmail) : vwUser.userEmail != null) return false;
        if (userQq != null ? !userQq.equals(vwUser.userQq) : vwUser.userQq != null) return false;
        if (userWechart != null ? !userWechart.equals(vwUser.userWechart) : vwUser.userWechart != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = roleIds != null ? roleIds.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (userAccount != null ? userAccount.hashCode() : 0);
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (userPassword != null ? userPassword.hashCode() : 0);
        result = 31 * result + (userSurname != null ? userSurname.hashCode() : 0);
        result = 31 * result + (userStatus != null ? userStatus.hashCode() : 0);
        result = 31 * result + (userType != null ? userType.hashCode() : 0);
        result = 31 * result + (corpId != null ? corpId.hashCode() : 0);
        result = 31 * result + (userPhone != null ? userPhone.hashCode() : 0);
        result = 31 * result + (whitelist != null ? whitelist.hashCode() : 0);
        result = 31 * result + (userGroup != null ? userGroup.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (createBy != null ? createBy.hashCode() : 0);
        result = 31 * result + (lastUpdateTime != null ? lastUpdateTime.hashCode() : 0);
        result = 31 * result + (lastUpdateBy != null ? lastUpdateBy.hashCode() : 0);
        result = 31 * result + (removeTime != null ? removeTime.hashCode() : 0);
        result = 31 * result + (userEmail != null ? userEmail.hashCode() : 0);
        result = 31 * result + (userQq != null ? userQq.hashCode() : 0);
        result = 31 * result + (userWechart != null ? userWechart.hashCode() : 0);
        return result;
    }
}
