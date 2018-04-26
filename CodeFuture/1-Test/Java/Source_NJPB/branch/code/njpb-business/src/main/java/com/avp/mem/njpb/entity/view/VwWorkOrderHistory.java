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
 * Created by six on 2017/7/31.
 */
@Entity
@Where(clause = "remove_time is null")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "vw_work_order_history", schema = "business")
public class VwWorkOrderHistory extends EntityBase {
    private String userName;
    private String txt;
    private Integer workOrderId;
    private Integer operationTypeId;
    private String operationRemark;
    private Integer operatorId;
    private Date lastOperationTime;
    private String operationTime;
    private Double longitude;
    private Double latitude;
    private Double workOrderScore;
    private Double workOrderScoreDeduct;
    @Basic
    @Column(name = "work_order_score")
    public Double getWorkOrderScore() {
        return workOrderScore;
    }

    public void setWorkOrderScore(Double workOrderScore) {
        this.workOrderScore = workOrderScore;
    }
    @Basic
    @Column(name = "work_order_score_deduct")
    public Double getWorkOrderScoreDeduct() {
        return workOrderScoreDeduct;
    }

    public void setWorkOrderScoreDeduct(Double workOrderScoreDeduct) {
        this.workOrderScoreDeduct = workOrderScoreDeduct;
    }

    @Basic
    @Column(name = "user_name")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "txt")
    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    @Basic
    @Column(name = "work_order_id")
    public Integer getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(Integer workOrderId) {
        this.workOrderId = workOrderId;
    }

    @Basic
    @Column(name = "operation_type_id")
    public Integer getOperationTypeId() {
        return operationTypeId;
    }

    public void setOperationTypeId(Integer operationTypeId) {
        this.operationTypeId = operationTypeId;
    }

    @Basic
    @Column(name = "operation_remark")
    public String getOperationRemark() {
        return operationRemark;
    }

    public void setOperationRemark(String operationRemark) {
        this.operationRemark = operationRemark;
    }

    @Basic
    @Column(name = "operator_id")
    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    @Basic
    @Column(name = "last_operation_time")
    public Date getLastOperationTime() {
        return lastOperationTime;
    }

    public void setLastOperationTime(Date lastOperationTime) {
        this.lastOperationTime = lastOperationTime;
    }

    @Basic
    @Column(name = "operation_time")
    public String getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(String operationTime) {
        this.operationTime = operationTime;
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
}
