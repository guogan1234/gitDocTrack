package com.avp.mem.njpb.oauth.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.WhereJoinTable;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

/**
 * @author Amber Wang
 * @date 2016-11-30
 */
@Data
@Entity
@Table(name = "sys_role", schema = "bussiness")
public class SysRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "role_name")
    private String roleName;

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

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(schema="bussiness", name = "sys_role_resource", joinColumns = { @JoinColumn(name = "role_id") }, inverseJoinColumns = @JoinColumn(name = "resource_id"))
    @WhereJoinTable(clause = "remove_time is null")
    private Collection<SysResource> sysResources;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Integer getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(Integer lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public Collection<SysResource> getSysResources() {
        return sysResources;
    }

    public void setSysResources(Collection<SysResource> sysResources) {
        this.sysResources = sysResources;
    }
    
}
