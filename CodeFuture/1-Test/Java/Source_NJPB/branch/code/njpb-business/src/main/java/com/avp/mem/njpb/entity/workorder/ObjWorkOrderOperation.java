/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.entity.workorder;

import com.avp.mem.njpb.api.entity.EntityBase;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.Where;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Created by six on 2017/7/24.
 */
@Entity
@Where(clause = "remove_time is null")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "obj_work_order_operation", schema = "business")
public class ObjWorkOrderOperation extends EntityBase {
    private Integer workOrderId;
    private Integer operationTypeId;
    private Integer operatorId;
    private String operationRemark;
    private Date lastOperationTime;
    private Double longitude;
    private Double latitude;
    private String location;

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
    @Column(name = "work_order_id")
    public Integer getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(Integer workOrderId) {
        this.workOrderId = workOrderId;
    }
    @Basic
    @Column(name = "location")
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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
    @Column(name = "operator_id")
    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    @Basic
    @Column(name = "operation_remark")
    public String getOperationRemark() {
        return operationRemark;
    }

    public void setOperationRemark(String operationRemark) {
        this.operationRemark = operationRemark;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_operation_time", nullable = false)
    public Date getLastOperationTime() {
        return this.lastOperationTime;
    }

    public void setLastOperationTime(Date lastOperationTime) {
        this.lastOperationTime = lastOperationTime;
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
