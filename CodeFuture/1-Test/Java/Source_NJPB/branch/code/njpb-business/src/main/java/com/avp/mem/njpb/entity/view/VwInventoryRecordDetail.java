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

/**
 * Created by six on 2017/8/7.
 */
@Entity
@Where(clause = "remove_time is null")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "vw_inventory_record_detail", schema = "business")
public class VwInventoryRecordDetail extends EntityBase {
    private Integer inventoryRecordId;
    private Integer estateId;
    private Integer corpId;
    private Integer estateTypeId;
    private Integer operationType;
    private Integer operator;
    private Integer stockId;
    private String estateName;
    private Integer stationId;
    private Integer category;
    private Integer estateStatusId;
    private String estateSn;
    private Integer supplierId;
    private String estateBatch;
    private Integer parentId;
    private Integer logicalId;
    private Integer projectId;
    private String estatePath;
    private Integer estateNo;


    @Basic
    @Column(name = "inventory_record_id")
    public Integer getInventoryRecordId() {
        return inventoryRecordId;
    }

    public void setInventoryRecordId(Integer inventoryRecordId) {
        this.inventoryRecordId = inventoryRecordId;
    }

    @Basic
    @Column(name = "estate_id")
    public Integer getEstateId() {
        return estateId;
    }

    public void setEstateId(Integer estateId) {
        this.estateId = estateId;
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
    @Column(name = "estate_type_id")
    public Integer getEstateTypeId() {
        return estateTypeId;
    }

    public void setEstateTypeId(Integer estateTypeId) {
        this.estateTypeId = estateTypeId;
    }

    @Basic
    @Column(name = "operation_type")
    public Integer getOperationType() {
        return operationType;
    }

    public void setOperationType(Integer operationType) {
        this.operationType = operationType;
    }

    @Basic
    @Column(name = "operator")
    public Integer getOperator() {
        return operator;
    }

    public void setOperator(Integer operator) {
        this.operator = operator;
    }

    @Basic
    @Column(name = "stock_id")
    public Integer getStockId() {
        return stockId;
    }

    public void setStockId(Integer stockId) {
        this.stockId = stockId;
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
    @Column(name = "estate_sn")
    public String getEstateSn() {
        return estateSn;
    }

    public void setEstateSn(String estateSn) {
        this.estateSn = estateSn;
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

}
