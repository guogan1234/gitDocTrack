/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.entity.system;

import com.avp.mem.njpb.api.entity.EntityBase;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author ambEr
 */
@Entity
@Where(clause = "remove_time is null")
@DynamicUpdate(true)
@DynamicInsert(true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "sys_user_point_record", schema = "business")
public class SysUserPointRecord extends EntityBase implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
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

    public SysUserPointRecord() {
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

}
