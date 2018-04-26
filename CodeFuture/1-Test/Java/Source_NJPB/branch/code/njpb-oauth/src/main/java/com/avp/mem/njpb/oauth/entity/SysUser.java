package com.avp.mem.njpb.oauth.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.hibernate.annotations.WhereJoinTable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * @author Amber Wang
 * @date 2016-11-30
 */
@Data
@Entity
@Table(name = "sys_user", schema = "business")
@JsonIgnoreProperties(value = {"userPassword", "whitelist", "userPhone", "expiryTime", "createTime", "createBy", "lastUpdateTime", "lastUpdateBy", "removeTime"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Basic(optional = false)
    @Column(name = "user_account", unique = true, length = 30)
    private String userAccount;

    @Column(name = "user_name", length = 40)
    private String userName;

    @Column(name = "user_password", length = 100)
    private String userPassword;

    @Column(name = "whitelist")
    private Integer whitelist;

    @Column(name = "user_phone", length = 20)
    private String userPhone;

    @Column(name = "expiry_time")
    private Date expiryTime;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "create_by")
    private Integer createBy;

    @Column(name = "last_update_time")
    private Date lastUpdateTime;

    @Column(name = "last_update_by")
    private Integer lastUpdateBy;

    @Column(name = "remove_time")
    private Date removeTime;

    @Column(name = "corp_id")
    private Integer corpId;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(schema = "business", name = "sys_user_role", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = @JoinColumn(name = "role_id"))
    @WhereJoinTable(clause = "remove_time is null")
    private Collection<SysRole> roleCollection;

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Collection<SysRole> getRoleCollection() {
        return roleCollection;
    }

    public void setRoleCollection(Collection<SysRole> roleCollection) {
        this.roleCollection = roleCollection;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(Date expiryTime) {
        this.expiryTime = expiryTime;
    }

    public Integer getCorpId() {
        return corpId;
    }

    public void setCorpId(Integer corpId) {
        this.corpId = corpId;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Map<GrantedAuthority, Date> result = new HashMap<>();
        for (SysRole r : roleCollection) {
            for (SysResource res : r.getSysResources()) {
                result.put(res, new Date());
            }
        }
        return result.keySet();
    }

    @Override
    public String getUsername() {
        //return this.userAccount;
        return this.id.toString();
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return this.userPassword;
    }

    @Override
    public boolean isAccountNonExpired() {
        if (this.expiryTime == null) {
            return false;
        }
        return new Date().compareTo(this.expiryTime) < 0;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        if(this.expiryTime==null){
            return false;
        }

        return null == this.removeTime || new Date().after(this.expiryTime);
    }
}
