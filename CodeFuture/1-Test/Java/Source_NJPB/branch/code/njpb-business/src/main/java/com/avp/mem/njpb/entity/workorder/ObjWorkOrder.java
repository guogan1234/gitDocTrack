/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.entity.workorder;

import com.avp.mem.njpb.api.entity.EntityBase;
import org.hibernate.annotations.Where;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by six on 2017/7/24.
 */
@Entity
//@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "obj_work_order", schema = "business")
@Where(clause = "remove_time is null")
public class ObjWorkOrder extends EntityBase {
    private Integer estateId;
    private Integer typeId;
    private String serialNo;
    private Integer reportEmployee;
    private Integer assignEmployee;
    private Date reportTime;
    private Date assignTime;
    private Integer repairEmployee;
    private Date repairConfirmTime;
    private Date repairStartTime;
    private Date repairEndTime;
    private Integer statusId;
    private String faultDescription;
    private String repairRemark;
    private String assignRemark;
    private Integer workOrderSource;
    private String maintainRemark;
    private Integer stationId;
    private Double longitude;
    private Double latitude;
    private Integer level;
    private Integer reponseOverTime;
    private Integer repairOverTime;
    private Integer reportWay;
    private String backRemark;
    private String processInstanceId;
    private Boolean fixed;
    private Date responseTimeOutDate;
    private Date repairTimeOutDate;
    private String location;
    private Double workOrderScore;
    private Double workOrderScoreDeduct;
    private String scoreDeductRemark;
    private Integer faultId;
    private Integer checkEmployee;
    private String closeRemark;
    private Date repairBackTime;
    private String repairBackRemark;




    @Column(name = "repair_back_time")
    public Date getRepairBackTime() {
        return repairBackTime;
    }

    public void setRepairBackTime(Date repairBackTime) {
        this.repairBackTime = repairBackTime;
    }

    @Column(name = "repair_back_remark")
    public String getRepairBackRemark() {
        return repairBackRemark;
    }

    public void setRepairBackRemark(String repairBackRemark) {
        this.repairBackRemark = repairBackRemark;
    }


    @Column(name = "close_remark")
    public String getCloseRemark() {
        return closeRemark;
    }

    public void setCloseRemark(String closeRemark) {
        this.closeRemark = closeRemark;
    }

    @Basic
    @Column(name = "fault_id")
    public Integer getFaultId() {
        return faultId;
    }

    public void setFaultId(Integer faultId) {
        this.faultId = faultId;
    }

    @Basic
    @Column(name = "check_employee")
    public Integer getCheckEmployee() {
        return checkEmployee;
    }

    public void setCheckEmployee(Integer checkEmployee) {
        this.checkEmployee = checkEmployee;
    }

    @Basic
    @Column(name = "estate_id")
    public Integer getEstateId() {
        return estateId;
    }

    public void setEstateId(Integer estateId) {
        this.estateId = estateId;
    }

    @Basic
    @Column(name = "type_id")
    public Integer getTypeId() {
        return typeId;
    }


    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
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
    @Column(name = "serial_no")
    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    @Basic
    @Column(name = "report_employee")
    public Integer getReportEmployee() {
        return reportEmployee;
    }

    public void setReportEmployee(Integer reportEmployee) {
        this.reportEmployee = reportEmployee;
    }

    @Basic
    @Column(name = "assign_employee")
    public Integer getAssignEmployee() {
        return assignEmployee;
    }

    public void setAssignEmployee(Integer assignEmployee) {
        this.assignEmployee = assignEmployee;
    }

    @Basic
    @Column(name = "report_time")
    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    @Basic
    @Column(name = "assign_time")
    public Date getAssignTime() {
        return assignTime;
    }

    public void setAssignTime(Date assignTime) {
        this.assignTime = assignTime;
    }

    @Basic
    @Column(name = "repair_employee")
    public Integer getRepairEmployee() {
        return repairEmployee;
    }

    public void setRepairEmployee(Integer repairEmployee) {
        this.repairEmployee = repairEmployee;
    }

    @Basic
    @Column(name = "repair_confirm_time")
    public Date getRepairConfirmTime() {
        return repairConfirmTime;
    }

    public void setRepairConfirmTime(Date repairConfirmTime) {
        this.repairConfirmTime = repairConfirmTime;
    }

    @Basic
    @Column(name = "repair_start_time")
    public Date getRepairStartTime() {
        return repairStartTime;
    }

    public void setRepairStartTime(Date repairStartTime) {
        this.repairStartTime = repairStartTime;
    }

    @Basic
    @Column(name = "repair_end_time")
    public Date getRepairEndTime() {
        return repairEndTime;
    }

    public void setRepairEndTime(Date repairEndTime) {
        this.repairEndTime = repairEndTime;
    }

    @Basic
    @Column(name = "status_id")
    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    @Basic
    @Column(name = "fault_description")
    public String getFaultDescription() {
        return faultDescription;
    }

    public void setFaultDescription(String faultDescription) {
        this.faultDescription = faultDescription;
    }

    @Basic
    @Column(name = "repair_remark")
    public String getRepairRemark() {
        return repairRemark;
    }

    public void setRepairRemark(String repairRemark) {
        this.repairRemark = repairRemark;
    }

    @Basic
    @Column(name = "assign_remark")
    public String getAssignRemark() {
        return assignRemark;
    }

    public void setAssignRemark(String assignRemark) {
        this.assignRemark = assignRemark;
    }

    @Basic
    @Column(name = "work_order_source")
    public Integer getWorkOrderSource() {
        return workOrderSource;
    }

    public void setWorkOrderSource(Integer workOrderSource) {
        this.workOrderSource = workOrderSource;
    }

    @Basic
    @Column(name = "maintain_remark")
    public String getMaintainRemark() {
        return maintainRemark;
    }

    public void setMaintainRemark(String maintainRemark) {
        this.maintainRemark = maintainRemark;
    }

    @Basic
    @Column(name = "station_id")
    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
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
    @Column(name = "level")
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Basic
    @Column(name = "reponse_over_time")
    public Integer getReponseOverTime() {
        return reponseOverTime;
    }

    public void setReponseOverTime(Integer reponseOverTime) {
        this.reponseOverTime = reponseOverTime;
    }

    @Basic
    @Column(name = "repair_over_time")
    public Integer getRepairOverTime() {
        return repairOverTime;
    }

    public void setRepairOverTime(Integer repairOverTime) {
        this.repairOverTime = repairOverTime;
    }

    @Column(name = "process_instance_id")
    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    @Column(name = "report_way")
    public Integer getReportWay() {
        return reportWay;
    }

    public void setReportWay(Integer reportWay) {
        this.reportWay = reportWay;
    }

    @Column(name = "back_remark")
    public String getBackRemark() {
        return backRemark;
    }

    public void setBackRemark(String backRemark) {
        this.backRemark = backRemark;
    }

    @Column(name = "fixed")
    public Boolean getFixed() {
        return fixed;
    }

    public void setFixed(Boolean fixed) {
        this.fixed = fixed;
    }

    @Column(name = "response_time_out_date")
    public Date getResponseTimeOutDate() {
        return responseTimeOutDate;
    }

    public void setResponseTimeOutDate(Date responseTimeOutDate) {
        this.responseTimeOutDate = responseTimeOutDate;
    }

    @Column(name = "repair_time_out_date")
    public Date getRepairTimeOutDate() {
        return repairTimeOutDate;
    }

    public void setRepairTimeOutDate(Date repairTimeOutDate) {
        this.repairTimeOutDate = repairTimeOutDate;
    }

    @Column(name = "work_order_score")
    public Double getWorkOrderScore() {
        return workOrderScore;
    }

    public void setWorkOrderScore(Double workOrderScore) {
        this.workOrderScore = workOrderScore;
    }
    @Column(name = "work_order_score_deduct")
    public Double getWorkOrderScoreDeduct() {
        return workOrderScoreDeduct;
    }

    public void setWorkOrderScoreDeduct(Double workOrderScoreDeduct) {
        this.workOrderScoreDeduct = workOrderScoreDeduct;
    }
    @Column(name = "score_deduct_remark")
    public String getScoreDeductRemark() {
        return scoreDeductRemark;
    }

    public void setScoreDeductRemark(String scoreDeductRemark) {
        this.scoreDeductRemark = scoreDeductRemark;
    }

    public ObjWorkOrder() {
    }

    public ObjWorkOrder(Integer estateId, Integer typeId, String serialNo, Integer reportEmployee, Integer assignEmployee, Date reportTime, Date assignTime, Integer repairEmployee, Date repairConfirmTime, Date repairStartTime, Date repairEndTime, Integer statusId, String faultDescription, String repairRemark, String assignRemark, Integer workOrderSource, String maintainRemark, Integer stationId, Double longitude, Double latitude, Integer level, Integer reponseOverTime, Integer repairOverTime, Integer reportWay, String backRemark, String processInstanceId, Boolean fixed, Date responseTimeOutDate, Date repairTimeOutDate) {
        this.estateId = estateId;
        this.typeId = typeId;
        this.serialNo = serialNo;
        this.reportEmployee = reportEmployee;
        this.assignEmployee = assignEmployee;
        this.reportTime = reportTime;
        this.assignTime = assignTime;
        this.repairEmployee = repairEmployee;
        this.repairConfirmTime = repairConfirmTime;
        this.repairStartTime = repairStartTime;
        this.repairEndTime = repairEndTime;
        this.statusId = statusId;
        this.faultDescription = faultDescription;
        this.repairRemark = repairRemark;
        this.assignRemark = assignRemark;
        this.workOrderSource = workOrderSource;
        this.maintainRemark = maintainRemark;
        this.stationId = stationId;
        this.longitude = longitude;
        this.latitude = latitude;
        this.level = level;
        this.reponseOverTime = reponseOverTime;
        this.repairOverTime = repairOverTime;
        this.reportWay = reportWay;
        this.backRemark = backRemark;
        this.processInstanceId = processInstanceId;
        this.fixed = fixed;
        this.responseTimeOutDate = responseTimeOutDate;
        this.repairTimeOutDate = repairTimeOutDate;
    }
}
