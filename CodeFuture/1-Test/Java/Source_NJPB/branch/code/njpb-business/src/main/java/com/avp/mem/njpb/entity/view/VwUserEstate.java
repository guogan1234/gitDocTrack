/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.entity.view;

import com.avp.mem.njpb.api.entity.EntityBase;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by six on 2017/7/26.
 */
@Entity
@Where(clause = "remove_time is null")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "vw_user_estate", schema = "business")
public class VwUserEstate extends EntityBase implements java.io.Serializable {
    @Column(name = "estate_no")
    private Integer estateNo;
    @Column(name = "estate_sn")
    private String estateSn;
    @Column(name = "estate_name")
    private String estateName;
    @Column(name = "estate_type_id")
    private Integer estateTypeId;
    @Column(name = "estate_type_name")
    private String estateTypeName;
    @Column(name = "estate_status_id")
    private Integer estateStatusId;
    @Column(name = "estate_status_name")
    private String estateStatusName;
    @Column(name = "logical_id")
    private Integer logicalId;
    @Column(name = "parent_id")
    private Integer parentId;
    @Column(name = "estate_path")
    private String estatePath;
    @Column(name = "project_id")
    private Integer projectId;
    @Column(name = "station_id")
    private Integer stationId;
    @Column(name = "station_no")
    private String stationNo;
    @Column(name = "station_sn")
    private String stationSn;
    @Column(name = "category")
    private Integer category;
    @Column(name = "station_name")
    private String stationName;
    @Column(name = "supplier_id")
    private Integer supplierId;
    @Column(name = "supplier_name")
    private String supplierName;
    @Column(name = "corp_name")
    private String corpName;
    @Column(name = "uid")
    private Integer uid;
    @Column(name = "bike_frame_no")
    private Integer bikeFrameNo;
    @Column(name = "creat_by_user_name")
    private String creatByUserName;
    @Column(name = "last_update_by_user_name")
    private String lastUpdateByUserName;
    @Column(name = "bicycle_stake_bar_code")
    private String bicycleStakeBarCode;

    public VwUserEstate() {
    }

    public Integer getEstateNo() {
        return estateNo;
    }

    public void setEstateNo(Integer estateNo) {
        this.estateNo = estateNo;
    }

    public String getEstateSn() {
        return estateSn;
    }

    public void setEstateSn(String estateSn) {
        this.estateSn = estateSn;
    }

    public String getEstateName() {
        return estateName;
    }

    public void setEstateName(String estateName) {
        this.estateName = estateName;
    }

    public Integer getEstateTypeId() {
        return estateTypeId;
    }

    public void setEstateTypeId(Integer estateTypeId) {
        this.estateTypeId = estateTypeId;
    }

    public String getEstateTypeName() {
        return estateTypeName;
    }

    public void setEstateTypeName(String estateTypeName) {
        this.estateTypeName = estateTypeName;
    }

    public Integer getEstateStatusId() {
        return estateStatusId;
    }

    public void setEstateStatusId(Integer estateStatusId) {
        this.estateStatusId = estateStatusId;
    }

    public String getEstateStatusName() {
        return estateStatusName;
    }

    public void setEstateStatusName(String estateStatusName) {
        this.estateStatusName = estateStatusName;
    }

    public Integer getLogicalId() {
        return logicalId;
    }

    public void setLogicalId(Integer logicalId) {
        this.logicalId = logicalId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getEstatePath() {
        return estatePath;
    }

    public void setEstatePath(String estatePath) {
        this.estatePath = estatePath;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public String getStationNo() {
        return stationNo;
    }

    public void setStationNo(String stationNo) {
        this.stationNo = stationNo;
    }

    public String getStationSn() {
        return stationSn;
    }

    public void setStationSn(String stationSn) {
        this.stationSn = stationSn;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getBikeFrameNo() {
        return bikeFrameNo;
    }

    public void setBikeFrameNo(Integer bikeFrameNo) {
        this.bikeFrameNo = bikeFrameNo;
    }

    public String getCreatByUserName() {
        return creatByUserName;
    }

    public void setCreatByUserName(String creatByUserName) {
        this.creatByUserName = creatByUserName;
    }

    public String getLastUpdateByUserName() {
        return lastUpdateByUserName;
    }

    public void setLastUpdateByUserName(String lastUpdateByUserName) {
        this.lastUpdateByUserName = lastUpdateByUserName;
    }

    public String getBicycleStakeBarCode() {
        return bicycleStakeBarCode;
    }

    public void setBicycleStakeBarCode(String bicycleStakeBarCode) {
        this.bicycleStakeBarCode = bicycleStakeBarCode;
    }
}
