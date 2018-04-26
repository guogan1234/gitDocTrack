/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.entity.view;

import com.avp.mem.njpb.api.entity.EntityBase;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.Where;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author ambEr
 */
@Entity
@Where(clause = "remove_time is null")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "vw_inventory_check_record", schema = "business")
public class VwInventoryCheckRecord extends EntityBase implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "corp_id")
    private Integer corpId;
    @Column(name = "count")
    private Integer count;
    @Column(name = "check_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date checkTime;
    @Column(name = "check_user_id")
    private Integer checkUserId;
    @Column(name = "check_remark")
    private String checkRemark;
    @Column(name = "station_id")
    private Integer stationId;
    @Column(name = "param_version")
    private Integer paramVersion;
    @Column(name = "station_name")
    private String stationName;
    @Column(name = "station_no")
    private String stationNo;
    @Column(name = "station_no_name")
    private String stationNoName;
    @Column(name = "corp_name")
    private String corpName;
    @Column(name = "check_user_name")
    private String checkUserName;

    public VwInventoryCheckRecord() {
    }

    public Integer getCorpId() {
        return corpId;
    }

    public void setCorpId(Integer corpId) {
        this.corpId = corpId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public Integer getCheckUserId() {
        return checkUserId;
    }

    public void setCheckUserId(Integer checkUserId) {
        this.checkUserId = checkUserId;
    }

    public String getCheckRemark() {
        return checkRemark;
    }

    public void setCheckRemark(String checkRemark) {
        this.checkRemark = checkRemark;
    }

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public Integer getParamVersion() {
        return paramVersion;
    }

    public void setParamVersion(Integer paramVersion) {
        this.paramVersion = paramVersion;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStationNo() {
        return stationNo;
    }

    public void setStationNo(String stationNo) {
        this.stationNo = stationNo;
    }

    public String getStationNoName() {
        return stationNoName;
    }

    public void setStationNoName(String stationNoName) {
        this.stationNoName = stationNoName;
    }

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    public String getCheckUserName() {
        return checkUserName;
    }

    public void setCheckUserName(String checkUserName) {
        this.checkUserName = checkUserName;
    }
}
