/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.entity.view;

import com.avp.mem.njpb.api.entity.EntityBase;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
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
@Table(name = "vw_user_point_record",  schema = "business")
public class VwUserPointRecord extends EntityBase implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "point_change")
    private String pointChange;
    @Column(name = "point_source")
    private Integer pointSource;
    @Column(name = "work_order_id")
    private Integer workOrderId;
    @Column(name = "current_date_str")
    private String currentDateStr;
    @Column(name = "point_record_level")
    private Integer pointRecordLevel;
    @Column(name = "user_account")
    private String userAccount;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "user_surname")
    private String userSurname;
    @Column(name = "corp_id")
    private Integer corpId;
    @Column(name = "corp_name")
    private String corpName;
    @Column(name = "user_phone")
    private String userPhone;
    @Column(name = "user_email")
    private String userEmail;
    @Column(name = "user_qq")
    private String userQq;
    @Column(name = "user_wechart")
    private String userWechart;
    @Column(name = "serial_no")
    private String serialNo;
    @Column(name = "station_no")
    private String stationNo;
    @Column(name = "station_name")
    private String stationName;
    @Column(name = "station_no_name")
    private String stationNoName;

    public VwUserPointRecord() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPointChange() {
        return pointChange;
    }

    public void setPointChange(String pointChange) {
        this.pointChange = pointChange;
    }

    public Integer getPointSource() {
        return pointSource;
    }

    public void setPointSource(Integer pointSource) {
        this.pointSource = pointSource;
    }

    public Integer getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(Integer workOrderId) {
        this.workOrderId = workOrderId;
    }

    public String getCurrentDateStr() {
        return currentDateStr;
    }

    public void setCurrentDateStr(String currentDateStr) {
        this.currentDateStr = currentDateStr;
    }

    public Integer getPointRecordLevel() {
        return pointRecordLevel;
    }

    public void setPointRecordLevel(Integer pointRecordLevel) {
        this.pointRecordLevel = pointRecordLevel;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public Integer getCorpId() {
        return corpId;
    }

    public void setCorpId(Integer corpId) {
        this.corpId = corpId;
    }

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserQq() {
        return userQq;
    }

    public void setUserQq(String userQq) {
        this.userQq = userQq;
    }

    public String getUserWechart() {
        return userWechart;
    }

    public void setUserWechart(String userWechart) {
        this.userWechart = userWechart;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getStationNo() {
        return stationNo;
    }

    public void setStationNo(String stationNo) {
        this.stationNo = stationNo;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStationNoName() {
        return stationNoName;
    }

    public void setStationNoName(String stationNoName) {
        this.stationNoName = stationNoName;
    }
    
}
