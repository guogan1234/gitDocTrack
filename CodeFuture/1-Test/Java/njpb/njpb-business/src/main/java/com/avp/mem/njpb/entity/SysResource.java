package com.avp.mem.njpb.entity;

import com.avp.mem.njpb.api.entity.EntityBase;
import org.hibernate.annotations.Where;

import javax.persistence.Basic;
import javax.persistence.Entity;
import java.util.Date;

/**
 * Created by six on 2017/7/17.
 */
@Entity
@Where(clause="remove_time is null")
@javax.persistence.Table(name = "sys_resource", schema = "bussiness")
public class SysResource extends EntityBase{

    private String resourceNo;

    @Basic
    @javax.persistence.Column(name = "resource_no")
    public String getResourceNo() {
        return resourceNo;
    }

    public void setResourceNo(String resourceNo) {
        this.resourceNo = resourceNo;
    }

    private String resourceCode;

    @Basic
    @javax.persistence.Column(name = "resource_code")
    public String getResourceCode() {
        return resourceCode;
    }

    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode;
    }

    private String resourceName;

    @Basic
    @javax.persistence.Column(name = "resource_name")
    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    private String resourceNameEn;

    @Basic
    @javax.persistence.Column(name = "resource_name_en")
    public String getResourceNameEn() {
        return resourceNameEn;
    }

    public void setResourceNameEn(String resourceNameEn) {
        this.resourceNameEn = resourceNameEn;
    }

    private Integer resourceType;

    @Basic
    @javax.persistence.Column(name = "resource_type")
    public Integer getResourceType() {
        return resourceType;
    }

    public void setResourceType(Integer resourceType) {
        this.resourceType = resourceType;
    }

    private Integer resourceStatus;

    @Basic
    @javax.persistence.Column(name = "resource_status")
    public Integer getResourceStatus() {
        return resourceStatus;
    }

    public void setResourceStatus(Integer resourceStatus) {
        this.resourceStatus = resourceStatus;
    }

    private Integer parentId;

    @Basic
    @javax.persistence.Column(name = "parent_id")
    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    private Integer resourceGrade;

    @Basic
    @javax.persistence.Column(name = "resource_grade")
    public Integer getResourceGrade() {
        return resourceGrade;
    }

    public void setResourceGrade(Integer resourceGrade) {
        this.resourceGrade = resourceGrade;
    }

    private Integer resourceSort;

    @Basic
    @javax.persistence.Column(name = "resource_sort")
    public Integer getResourceSort() {
        return resourceSort;
    }

    public void setResourceSort(Integer resourceSort) {
        this.resourceSort = resourceSort;
    }

    private String resourceUrl;

    @Basic
    @javax.persistence.Column(name = "resource_url")
    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    private String resourceEvent;

    @Basic
    @javax.persistence.Column(name = "resource_event")
    public String getResourceEvent() {
        return resourceEvent;
    }

    public void setResourceEvent(String resourceEvent) {
        this.resourceEvent = resourceEvent;
    }

    private String resourceEventFunc;

    @Basic
    @javax.persistence.Column(name = "resource_event_func")
    public String getResourceEventFunc() {
        return resourceEventFunc;
    }

    public void setResourceEventFunc(String resourceEventFunc) {
        this.resourceEventFunc = resourceEventFunc;
    }

    private String resourceImage;

    @Basic
    @javax.persistence.Column(name = "resource_image")
    public String getResourceImage() {
        return resourceImage;
    }

    public void setResourceImage(String resourceImage) {
        this.resourceImage = resourceImage;
    }

    private String resourceClass;

    @Basic
    @javax.persistence.Column(name = "resource_class")
    public String getResourceClass() {
        return resourceClass;
    }

    public void setResourceClass(String resourceClass) {
        this.resourceClass = resourceClass;
    }

    private String resourceStyle;

    @Basic
    @javax.persistence.Column(name = "resource_style")
    public String getResourceStyle() {
        return resourceStyle;
    }

    public void setResourceStyle(String resourceStyle) {
        this.resourceStyle = resourceStyle;
    }

    private String resourceRemark;

    @Basic
    @javax.persistence.Column(name = "resource_remark")
    public String getResourceRemark() {
        return resourceRemark;
    }

    public void setResourceRemark(String resourceRemark) {
        this.resourceRemark = resourceRemark;
    }

    private String resourceT1;

    @Basic
    @javax.persistence.Column(name = "resource_t1")
    public String getResourceT1() {
        return resourceT1;
    }

    public void setResourceT1(String resourceT1) {
        this.resourceT1 = resourceT1;
    }

    private String resourceT2;

    @Basic
    @javax.persistence.Column(name = "resource_t2")
    public String getResourceT2() {
        return resourceT2;
    }

    public void setResourceT2(String resourceT2) {
        this.resourceT2 = resourceT2;
    }

    private String resourceT3;

    @Basic
    @javax.persistence.Column(name = "resource_t3")
    public String getResourceT3() {
        return resourceT3;
    }

    public void setResourceT3(String resourceT3) {
        this.resourceT3 = resourceT3;
    }

    private Date createTime;

    @Basic
    @javax.persistence.Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    private Integer createBy;

    @Basic
    @javax.persistence.Column(name = "create_by")
    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    private Date lastUpdateTime;

    @Basic
    @javax.persistence.Column(name = "last_update_time")
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    private Integer lastUpdateBy;

    @Basic
    @javax.persistence.Column(name = "last_update_by")
    public Integer getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(Integer lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    private Date removeTime;

    @Basic
    @javax.persistence.Column(name = "remove_time")
    public Date getRemoveTime() {
        return removeTime;
    }

    public void setRemoveTime(Date removeTime) {
        this.removeTime = removeTime;
    }


}
