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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * @author ambEr
 */
@Entity
@Table(name = "vw_user_estate_bar_code", schema = "business")
@Where(clause = "remove_time is null")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VwUserEstateBarCode extends EntityBase implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "bar_code_path")
    private String barCodePath;
    @Column(name = "bar_code_sn")
    private String barCodeSn;
    @Column(name = "bar_code_message")
    private String barCodeMessage;
    @Column(name = "bar_code_category")
    private Integer barCodeCategory;
    @Column(name = "export_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date exportTime;
    @Column(name = "activate_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date activateTime;
    @Column(name = "relation")
    private Integer relation;
    @Column(name = "estate_name")
    private String estateName;
    @Column(name = "estate_no")
    private Integer estateNo;
    @Column(name = "station_id")
    private Integer stationId;
    @Column(name = "category")
    private Integer category;
    @Column(name = "estate_type_id")
    private Integer estateTypeId;
    @Column(name = "project_id")
    private Integer projectId;
    @Column(name = "supplier_id")
    private Integer supplierId;
    @Column(name = "bike_frame_no")
    private Integer bikeFrameNo;
    @Column(name = "station_name")
    private String stationName;
    @Column(name = "corp_name")
    private String corpName;
    @Column(name = "estate_type_name")
    private String estateTypeName;
    @Column(name = "supplier_name")
    private String supplierName;
    @Column(name = "uid")
    private Integer uid;
    @Column(name = "creat_by_user_name")
    private String creatByUserName;
    @Column(name = "last_update_by_user_name")
    private String lastUpdateByUserName;
    @Column(name = "station_no_name")
    private String stationNoName;
    @Column(name = "bicycle_stake_bar_code")
    private String bicycleStakeBarCode;
    public VwUserEstateBarCode() {
    }

    public String getBarCodePath() {
        return barCodePath;
    }

    public void setBarCodePath(String barCodePath) {
        this.barCodePath = barCodePath;
    }

    public String getBarCodeSn() {
        return barCodeSn;
    }

    public void setBarCodeSn(String barCodeSn) {
        this.barCodeSn = barCodeSn;
    }

    public String getBarCodeMessage() {
        return barCodeMessage;
    }

    public void setBarCodeMessage(String barCodeMessage) {
        this.barCodeMessage = barCodeMessage;
    }

    public Integer getBarCodeCategory() {
        return barCodeCategory;
    }

    public void setBarCodeCategory(Integer barCodeCategory) {
        this.barCodeCategory = barCodeCategory;
    }

    public Date getExportTime() {
        return exportTime;
    }

    public void setExportTime(Date exportTime) {
        this.exportTime = exportTime;
    }

    public Date getActivateTime() {
        return activateTime;
    }

    public void setActivateTime(Date activateTime) {
        this.activateTime = activateTime;
    }

    public Integer getRelation() {
        return relation;
    }

    public void setRelation(Integer relation) {
        this.relation = relation;
    }

    public String getEstateName() {
        return estateName;
    }

    public void setEstateName(String estateName) {
        this.estateName = estateName;
    }

    public Integer getEstateNo() {
        return estateNo;
    }

    public void setEstateNo(Integer estateNo) {
        this.estateNo = estateNo;
    }

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Integer getEstateTypeId() {
        return estateTypeId;
    }

    public void setEstateTypeId(Integer estateTypeId) {
        this.estateTypeId = estateTypeId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getBikeFrameNo() {
        return bikeFrameNo;
    }

    public void setBikeFrameNo(Integer bikeFrameNo) {
        this.bikeFrameNo = bikeFrameNo;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    public String getEstateTypeName() {
        return estateTypeName;
    }

    public void setEstateTypeName(String estateTypeName) {
        this.estateTypeName = estateTypeName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
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

    public String getStationNoName() {
        return stationNoName;
    }

    public void setStationNoName(String stationNoName) {
        this.stationNoName = stationNoName;
    }

    public String getBicycleStakeBarCode() {
        return bicycleStakeBarCode;
    }

    public void setBicycleStakeBarCode(String bicycleStakeBarCode) {
        this.bicycleStakeBarCode = bicycleStakeBarCode;
    }
}
