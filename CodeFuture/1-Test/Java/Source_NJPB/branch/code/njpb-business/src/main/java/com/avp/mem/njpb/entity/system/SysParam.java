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
import java.util.Date;

/**
 * Created by six on 2017/7/24.
 */
@Entity
@Where(clause = "remove_time is null")
@JsonInclude(JsonInclude.Include.NON_NULL)
@DynamicUpdate(true)
@DynamicInsert(true)
@Table(name = "sys_param", schema = "business")
public class SysParam extends EntityBase {

    @Column(name = "work_order_level_one_response_time")
    private Integer workOrderLevelOneResponseTime;
    @Column(name = "work_order_level_one_repair_time")
    private Integer workOrderLevelOneRepairTime;
    @Column(name = "work_order_level_two_response_time")
    private Integer workOrderLevelTwoResponseTime;
    @Column(name = "work_order_level_two_repair_time")
    private Integer workOrderLevelTwoRepairTime;
    @Column(name = "work_order_level_three_response_time")
    private Integer workOrderLevelThreeResponseTime;
    @Column(name = "work_order_level_three_repair_time")
    private Integer workOrderLevelThreeRepairTime;
    @Column(name = "work_start_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date workStartTime;
    @Column(name = "work_end_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date workEndTime;
    @Column(name = "work_period")
    private String workPeriod;
    @Column(name = "stock_check_start_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date stockCheckStartTime;
    @Column(name = "stock_check_end_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date stockCheckEndTime;
    @Column(name = "stock_check_version")
    private Integer stockCheckVersion;
    @Column(name = "work_order_level_emergency_response_time")
    private Integer workOrderLevelEmergencyResponseTime;
    @Column(name = "work_order_level_emergency_repair_time")
    private Integer workOrderLevelEmergencyRepairTime;

    public SysParam() {
    }

    public Integer getWorkOrderLevelOneResponseTime() {
        return workOrderLevelOneResponseTime;
    }

    public void setWorkOrderLevelOneResponseTime(Integer workOrderLevelOneResponseTime) {
        this.workOrderLevelOneResponseTime = workOrderLevelOneResponseTime;
    }

    public Integer getWorkOrderLevelOneRepairTime() {
        return workOrderLevelOneRepairTime;
    }

    public void setWorkOrderLevelOneRepairTime(Integer workOrderLevelOneRepairTime) {
        this.workOrderLevelOneRepairTime = workOrderLevelOneRepairTime;
    }

    public Integer getWorkOrderLevelTwoResponseTime() {
        return workOrderLevelTwoResponseTime;
    }

    public void setWorkOrderLevelTwoResponseTime(Integer workOrderLevelTwoResponseTime) {
        this.workOrderLevelTwoResponseTime = workOrderLevelTwoResponseTime;
    }

    public Integer getWorkOrderLevelTwoRepairTime() {
        return workOrderLevelTwoRepairTime;
    }

    public void setWorkOrderLevelTwoRepairTime(Integer workOrderLevelTwoRepairTime) {
        this.workOrderLevelTwoRepairTime = workOrderLevelTwoRepairTime;
    }

    public Integer getWorkOrderLevelThreeResponseTime() {
        return workOrderLevelThreeResponseTime;
    }

    public void setWorkOrderLevelThreeResponseTime(Integer workOrderLevelThreeResponseTime) {
        this.workOrderLevelThreeResponseTime = workOrderLevelThreeResponseTime;
    }

    public Integer getWorkOrderLevelThreeRepairTime() {
        return workOrderLevelThreeRepairTime;
    }

    public void setWorkOrderLevelThreeRepairTime(Integer workOrderLevelThreeRepairTime) {
        this.workOrderLevelThreeRepairTime = workOrderLevelThreeRepairTime;
    }

    public Date getWorkStartTime() {
        return workStartTime;
    }

    public void setWorkStartTime(Date workStartTime) {
        this.workStartTime = workStartTime;
    }

    public Date getWorkEndTime() {
        return workEndTime;
    }

    public void setWorkEndTime(Date workEndTime) {
        this.workEndTime = workEndTime;
    }

    public String getWorkPeriod() {
        return workPeriod;
    }

    public void setWorkPeriod(String workPeriod) {
        this.workPeriod = workPeriod;
    }

    public Date getStockCheckStartTime() {
        return stockCheckStartTime;
    }

    public void setStockCheckStartTime(Date stockCheckStartTime) {
        this.stockCheckStartTime = stockCheckStartTime;
    }

    public Date getStockCheckEndTime() {
        return stockCheckEndTime;
    }

    public void setStockCheckEndTime(Date stockCheckEndTime) {
        this.stockCheckEndTime = stockCheckEndTime;
    }

    public Integer getStockCheckVersion() {
        return stockCheckVersion;
    }

    public void setStockCheckVersion(Integer stockCheckVersion) {
        this.stockCheckVersion = stockCheckVersion;
    }

    public Integer getWorkOrderLevelEmergencyResponseTime() {
        return workOrderLevelEmergencyResponseTime;
    }

    public void setWorkOrderLevelEmergencyResponseTime(Integer workOrderLevelEmergencyResponseTime) {
        this.workOrderLevelEmergencyResponseTime = workOrderLevelEmergencyResponseTime;
    }

    public Integer getWorkOrderLevelEmergencyRepairTime() {
        return workOrderLevelEmergencyRepairTime;
    }

    public void setWorkOrderLevelEmergencyRepairTime(Integer workOrderLevelEmergencyRepairTime) {
        this.workOrderLevelEmergencyRepairTime = workOrderLevelEmergencyRepairTime;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SysParam{");
        sb.append("workOrderLevelOneResponseTime=").append(workOrderLevelOneResponseTime);
        sb.append(", workOrderLevelOneRepairTime=").append(workOrderLevelOneRepairTime);
        sb.append(", workOrderLevelTwoResponseTime=").append(workOrderLevelTwoResponseTime);
        sb.append(", workOrderLevelTwoRepairTime=").append(workOrderLevelTwoRepairTime);
        sb.append(", workOrderLevelThreeResponseTime=").append(workOrderLevelThreeResponseTime);
        sb.append(", workOrderLevelThreeRepairTime=").append(workOrderLevelThreeRepairTime);
        sb.append(", workStartTime=").append(workStartTime);
        sb.append(", workEndTime=").append(workEndTime);
        sb.append(", workPeriod=").append(workPeriod);
        sb.append(", stockCheckStartTime=").append(stockCheckStartTime);
        sb.append(", stockCheckEndTime=").append(stockCheckEndTime);
        sb.append(", stockCheckVersion=").append(stockCheckVersion);
        sb.append(", workOrderLevelEmergencyResponseTime=").append(workOrderLevelEmergencyResponseTime);
        sb.append(", workOrderLevelEmergencyRepairTime=").append(workOrderLevelEmergencyRepairTime);
        sb.append('}');
        return sb.toString();
    }
}
