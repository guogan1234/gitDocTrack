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
 *
 * @author ambEr
 */
@Entity
@Where(clause = "remove_time is null")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "vw_user_position_record", catalog = "mem-pb-dev", schema = "business")
public class VwUserPositionRecord extends EntityBase implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "user_id")
    private Integer userId;
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
    @Column(name = "case")
    private String case1;
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
    @Column(name = "estate_id")
    private Integer estateId;
    @Column(name = "report_employee")
    private Integer reportEmployee;
    @Column(name = "report_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date reportTime;
    @Column(name = "assign_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date assignTime;
    @Column(name = "repair_employee")
    private Integer repairEmployee;
    @Column(name = "repair_confirm_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date repairConfirmTime;
    @Column(name = "repair_start_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date repairStartTime;
    @Column(name = "repair_end_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date repairEndTime;
    @Column(name = "status_id")
    private Integer statusId;
    @Column(name = "fault_description")
    private String faultDescription;
    @Column(name = "repair_remark")
    private String repairRemark;
    @Column(name = "assign_remark")
    private String assignRemark;
    @Column(name = "work_order_source")
    private Integer workOrderSource;
    @Column(name = "maintain_remark")
    private String maintainRemark;
    @Column(name = "station_id")
    private Integer stationId;
    @Column(name = "level")
    private Integer level;
    @Column(name = "reponse_over_time")
    private Integer reponseOverTime;
    @Column(name = "repair_over_time")
    private Integer repairOverTime;
    @Column(name = "assign_employee")
    private Integer assignEmployee;
    @Column(name = "report_way")
    private Integer reportWay;
    @Column(name = "serial_no")
    private String serialNo;
    @Column(name = "back_remark")
    private String backRemark;
    @Column(name = "project_id")
    private Integer projectId;
    @Column(name = "estate_type_name")
    private String estateTypeName;
    @Column(name = "estate_type_name_en")
    private String estateTypeNameEn;
    @Column(name = "estate_no")
    private Integer estateNo;
    @Column(name = "estate_name")
    private String estateName;
    @Column(name = "estate_type_id")
    private Integer estateTypeId;
    @Column(name = "category")
    private Integer category;
    @Column(name = "estate_sn")
    private String estateSn;
    @Column(name = "report_employee_user_name")
    private String reportEmployeeUserName;
    @Column(name = "assign_employee_user_name")
    private String assignEmployeeUserName;
    @Column(name = "repair_employee_user_name")
    private String repairEmployeeUserName;
    @Column(name = "work_order_type_name_cn")
    private String workOrderTypeNameCn;
    @Column(name = "work_order_status_name_cn")
    private String workOrderStatusNameCn;
    @Column(name = "level_color")
    private String levelColor;
    @Column(name = "station_name")
    private String stationName;
    @Column(name = "supplier_name")
    private String supplierName;
    @Column(name = "fixed")
    private Boolean fixed;
    @Column(name = "response_time_out_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date responseTimeOutDate;
    @Column(name = "repair_time_out_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date repairTimeOutDate;
    @Column(name = "bike_frame_no")
    private Integer bikeFrameNo;

    @Column(name = "location")
    private String location;




    public VwUserPositionRecord() {
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

    public String getCase1() {
        return case1;
    }

    public void setCase1(String case1) {
        this.case1 = case1;
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

    public Integer getEstateId() {
        return estateId;
    }

    public void setEstateId(Integer estateId) {
        this.estateId = estateId;
    }

    public Integer getReportEmployee() {
        return reportEmployee;
    }

    public void setReportEmployee(Integer reportEmployee) {
        this.reportEmployee = reportEmployee;
    }

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    public Date getAssignTime() {
        return assignTime;
    }

    public void setAssignTime(Date assignTime) {
        this.assignTime = assignTime;
    }

    public Integer getRepairEmployee() {
        return repairEmployee;
    }

    public void setRepairEmployee(Integer repairEmployee) {
        this.repairEmployee = repairEmployee;
    }

    public Date getRepairConfirmTime() {
        return repairConfirmTime;
    }

    public void setRepairConfirmTime(Date repairConfirmTime) {
        this.repairConfirmTime = repairConfirmTime;
    }

    public Date getRepairStartTime() {
        return repairStartTime;
    }

    public void setRepairStartTime(Date repairStartTime) {
        this.repairStartTime = repairStartTime;
    }

    public Date getRepairEndTime() {
        return repairEndTime;
    }

    public void setRepairEndTime(Date repairEndTime) {
        this.repairEndTime = repairEndTime;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public String getFaultDescription() {
        return faultDescription;
    }

    public void setFaultDescription(String faultDescription) {
        this.faultDescription = faultDescription;
    }

    public String getRepairRemark() {
        return repairRemark;
    }

    public void setRepairRemark(String repairRemark) {
        this.repairRemark = repairRemark;
    }

    public String getAssignRemark() {
        return assignRemark;
    }

    public void setAssignRemark(String assignRemark) {
        this.assignRemark = assignRemark;
    }

    public Integer getWorkOrderSource() {
        return workOrderSource;
    }

    public void setWorkOrderSource(Integer workOrderSource) {
        this.workOrderSource = workOrderSource;
    }

    public String getMaintainRemark() {
        return maintainRemark;
    }

    public void setMaintainRemark(String maintainRemark) {
        this.maintainRemark = maintainRemark;
    }

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getReponseOverTime() {
        return reponseOverTime;
    }

    public void setReponseOverTime(Integer reponseOverTime) {
        this.reponseOverTime = reponseOverTime;
    }

    public Integer getRepairOverTime() {
        return repairOverTime;
    }

    public void setRepairOverTime(Integer repairOverTime) {
        this.repairOverTime = repairOverTime;
    }

    public Integer getAssignEmployee() {
        return assignEmployee;
    }

    public void setAssignEmployee(Integer assignEmployee) {
        this.assignEmployee = assignEmployee;
    }

    public Integer getReportWay() {
        return reportWay;
    }

    public void setReportWay(Integer reportWay) {
        this.reportWay = reportWay;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getBackRemark() {
        return backRemark;
    }

    public void setBackRemark(String backRemark) {
        this.backRemark = backRemark;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getEstateTypeName() {
        return estateTypeName;
    }

    public void setEstateTypeName(String estateTypeName) {
        this.estateTypeName = estateTypeName;
    }

    public String getEstateTypeNameEn() {
        return estateTypeNameEn;
    }

    public void setEstateTypeNameEn(String estateTypeNameEn) {
        this.estateTypeNameEn = estateTypeNameEn;
    }

    public Integer getEstateNo() {
        return estateNo;
    }

    public void setEstateNo(Integer estateNo) {
        this.estateNo = estateNo;
    }

    public String getEstateName() {
        return estateName;
    }

    public void setEstateName(String estateName) {
        this.estateName = estateName;
    }

    public Integer getEstateTypeId() {
        return estateTypeId;
    }

    public void setEstateTypeId(Integer estateTypeId) {
        this.estateTypeId = estateTypeId;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getEstateSn() {
        return estateSn;
    }

    public void setEstateSn(String estateSn) {
        this.estateSn = estateSn;
    }

    public String getReportEmployeeUserName() {
        return reportEmployeeUserName;
    }

    public void setReportEmployeeUserName(String reportEmployeeUserName) {
        this.reportEmployeeUserName = reportEmployeeUserName;
    }

    public String getAssignEmployeeUserName() {
        return assignEmployeeUserName;
    }

    public void setAssignEmployeeUserName(String assignEmployeeUserName) {
        this.assignEmployeeUserName = assignEmployeeUserName;
    }

    public String getRepairEmployeeUserName() {
        return repairEmployeeUserName;
    }

    public void setRepairEmployeeUserName(String repairEmployeeUserName) {
        this.repairEmployeeUserName = repairEmployeeUserName;
    }

    public String getWorkOrderTypeNameCn() {
        return workOrderTypeNameCn;
    }

    public void setWorkOrderTypeNameCn(String workOrderTypeNameCn) {
        this.workOrderTypeNameCn = workOrderTypeNameCn;
    }

    public String getWorkOrderStatusNameCn() {
        return workOrderStatusNameCn;
    }

    public void setWorkOrderStatusNameCn(String workOrderStatusNameCn) {
        this.workOrderStatusNameCn = workOrderStatusNameCn;
    }

    public String getLevelColor() {
        return levelColor;
    }

    public void setLevelColor(String levelColor) {
        this.levelColor = levelColor;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Boolean getFixed() {
        return fixed;
    }

    public void setFixed(Boolean fixed) {
        this.fixed = fixed;
    }

    public Date getResponseTimeOutDate() {
        return responseTimeOutDate;
    }

    public void setResponseTimeOutDate(Date responseTimeOutDate) {
        this.responseTimeOutDate = responseTimeOutDate;
    }

    public Date getRepairTimeOutDate() {
        return repairTimeOutDate;
    }

    public void setRepairTimeOutDate(Date repairTimeOutDate) {
        this.repairTimeOutDate = repairTimeOutDate;
    }

    public Integer getBikeFrameNo() {
        return bikeFrameNo;
    }

    public void setBikeFrameNo(Integer bikeFrameNo) {
        this.bikeFrameNo = bikeFrameNo;
    }
}
