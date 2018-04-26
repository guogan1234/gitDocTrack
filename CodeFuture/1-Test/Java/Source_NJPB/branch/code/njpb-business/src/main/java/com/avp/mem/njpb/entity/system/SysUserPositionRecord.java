/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.entity.system;

import com.avp.mem.njpb.api.entity.EntityBase;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author ambEr
 */
@Entity
@Where(clause = "remove_time is null")
@DynamicUpdate(true)
@DynamicInsert(true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "sys_user_position_record", schema = "business")
public class SysUserPositionRecord extends EntityBase implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "user_id")
    private Integer userId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "longitude")
    private Double longitude;
    @Column(name = "latitude")
    private Double latitude;
    @Column(name = "position_source")
    private Integer positionSource;
    @Column(name = "work_order_id")
    private Integer workOrderId;
    @Column(name = "record_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date recordTime;

    @Column(name = "location")
    private String location;
    public SysUserPositionRecord() {
    }

    public SysUserPositionRecord(Integer userId, Double longitude, Double latitude, String location, Integer positionSource, Integer workOrderId, Date recordTime) {
        this.userId = userId;
        this.longitude = longitude;
        this.latitude = latitude;
        this.location = location;
        this.positionSource = positionSource;
        this.workOrderId = workOrderId;
        this.recordTime = recordTime;
    }
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Integer getPositionSource() {
        return positionSource;
    }

    public void setPositionSource(Integer positionSource) {
        this.positionSource = positionSource;
    }

    public Integer getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(Integer workOrderId) {
        this.workOrderId = workOrderId;
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

}
