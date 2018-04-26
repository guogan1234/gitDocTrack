/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.entity.view;

import com.avp.mem.njpb.api.entity.EntityBase;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.Where;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by six on 2017/8/8.
 */
@Entity
@Where(clause = "remove_time is null")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "vw_inventory_check_record_detail", schema = "business")
public class VwInventoryCheckRecordDetail extends EntityBase {
    private Integer inventoryCheckRecordId;
    private String estateSn;
    private Integer corpId;
    private Integer count;
    private Date checkTime;
    private Integer checkUserId;
    private String checkRemark;
    private Integer paramVersion;
    private String estateName;
    private Integer stationId;
    private String stationName;
    private String stationNo;
    private String stationNoName;
    private Integer category;
    private Integer estateStatusId;
    private Integer supplierId;
    private String estateBatch;
    private Integer parentId;
    private Integer logicalId;
    private Integer projectId;
    private String estatePath;
    private Integer estateNo;


    @Basic
    @Column(name = "inventory_check_record_id")
    public Integer getInventoryCheckRecordId() {
        return inventoryCheckRecordId;
    }

    public void setInventoryCheckRecordId(Integer inventoryCheckRecordId) {
        this.inventoryCheckRecordId = inventoryCheckRecordId;
    }

    @Basic
    @Column(name = "estate_sn")
    public String getEstateSn() {
        return estateSn;
    }

    public void setEstateSn(String estateSn) {
        this.estateSn = estateSn;
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
    @Column(name = "check_time")
    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    @Basic
    @Column(name = "check_user_id")
    public Integer getCheckUserId() {
        return checkUserId;
    }

    public void setCheckUserId(Integer checkUserId) {
        this.checkUserId = checkUserId;
    }

    @Basic
    @Column(name = "check_remark")
    public String getCheckRemark() {
        return checkRemark;
    }

    public void setCheckRemark(String checkRemark) {
        this.checkRemark = checkRemark;
    }

    @Basic
    @Column(name = "estate_name")
    public String getEstateName() {
        return estateName;
    }

    public void setEstateName(String estateName) {
        this.estateName = estateName;
    }

    @Basic
    @Column(name = "station_id")
    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    @Basic
    @Column(name = "station_name")
    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    @Basic
    @Column(name = "category")
    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    @Basic
    @Column(name = "estate_status_id")
    public Integer getEstateStatusId() {
        return estateStatusId;
    }

    public void setEstateStatusId(Integer estateStatusId) {
        this.estateStatusId = estateStatusId;
    }

    @Basic
    @Column(name = "supplier_id")
    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    @Basic
    @Column(name = "estate_batch")
    public String getEstateBatch() {
        return estateBatch;
    }

    public void setEstateBatch(String estateBatch) {
        this.estateBatch = estateBatch;
    }

    @Basic
    @Column(name = "parent_id")
    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    @Basic
    @Column(name = "logical_id")
    public Integer getLogicalId() {
        return logicalId;
    }

    public void setLogicalId(Integer logicalId) {
        this.logicalId = logicalId;
    }

    @Basic
    @Column(name = "project_id")
    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    @Basic
    @Column(name = "estate_path")
    public String getEstatePath() {
        return estatePath;
    }

    public void setEstatePath(String estatePath) {
        this.estatePath = estatePath;
    }

    @Basic
    @Column(name = "estate_no")
    public Integer getEstateNo() {
        return estateNo;
    }

    public void setEstateNo(Integer estateNo) {
        this.estateNo = estateNo;
    }

    @Basic
    @Column(name = "param_version")
    public Integer getParamVersion() {
        return paramVersion;
    }

    public void setParamVersion(Integer paramVersion) {
        this.paramVersion = paramVersion;
    }

    @Basic
    @Column(name = "count")
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Basic
    @Column(name = "station_no")
    public String getStationNo() {
        return stationNo;
    }

    public void setStationNo(String stationNo) {
        this.stationNo = stationNo;
    }

    @Basic
    @Column(name = "station_no_name")
    public String getStationNoName() {
        return stationNoName;
    }

    public void setStationNoName(String stationNoName) {
        this.stationNoName = stationNoName;
    }
}
