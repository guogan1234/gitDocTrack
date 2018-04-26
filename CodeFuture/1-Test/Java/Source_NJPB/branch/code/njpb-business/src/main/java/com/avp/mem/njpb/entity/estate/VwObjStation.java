/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.entity.estate;

import com.avp.mem.njpb.api.entity.EntityBase;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.Where;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by six on 2017/8/10.
 */
@Entity
@Where(clause = "remove_time is null")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "vw_obj_station", schema = "business")
public class VwObjStation extends EntityBase {
    private Integer projectId;
    private String corpName;
    private String stationNo;
    private String stationSn;
    private String stationName;
    private String stationEn;
    private String stationNameShort;
    private String stationNoName;
    private Double longitude;
    private Double latitude;
    private String remark;
    private String principal;
    private Integer checkUserId;
    private String checkUserName;
    private Integer estateCount;
    private String stationMessage;
    @Basic
    @Column(name = "estate_count")
    public Integer getEstateCount() {
        return estateCount;
    }

    public void setEstateCount(Integer estateCount) {
        this.estateCount = estateCount;
    }
    @Basic
    @Column(name = "station_message")
    public String getStationMessage() {
        return stationMessage;
    }

    public void setStationMessage(String stationMessage) {
        this.stationMessage = stationMessage;
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
    @Column(name = "check_user_name")
    public String getCheckUserName() {
        return checkUserName;
    }

    public void setCheckUserName(String checkUserName) {
        this.checkUserName = checkUserName;
    }

    @Basic
    @Column(name = "corp_name")
    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
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
    @Column(name = "station_no")
    public String getStationNo() {
        return stationNo;
    }

    public void setStationNo(String stationNo) {
        this.stationNo = stationNo;
    }

    @Basic
    @Column(name = "station_sn")
    public String getStationSn() {
        return stationSn;
    }

    public void setStationSn(String stationSn) {
        this.stationSn = stationSn;
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
    @Column(name = "station_en")
    public String getStationEn() {
        return stationEn;
    }

    public void setStationEn(String stationEn) {
        this.stationEn = stationEn;
    }

    @Basic
    @Column(name = "station_name_short")
    public String getStationNameShort() {
        return stationNameShort;
    }

    public void setStationNameShort(String stationNameShort) {
        this.stationNameShort = stationNameShort;
    }

    @Basic
    @Column(name = "station_no_name")
    public String getStationNoName() {
        return stationNoName;
    }

    public void setStationNoName(String stationNoName) {
        this.stationNoName = stationNoName;
    }

    @Basic
    @Column(name = "longitude")
    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Basic
    @Column(name = "latitude")
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @Basic
    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Basic
    @Column(name = "principal")
    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }


}
