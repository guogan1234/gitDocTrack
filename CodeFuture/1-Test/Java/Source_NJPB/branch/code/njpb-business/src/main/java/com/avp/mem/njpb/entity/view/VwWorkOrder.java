/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.entity.view;

import com.avp.mem.njpb.api.entity.EntityBase;
import org.hibernate.annotations.Where;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Created by six on 2017/7/28.
 */
@Entity
@Where(clause = "remove_time is null")
//@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "vw_work_order", schema = "business")
public class VwWorkOrder extends EntityBase {
    private static final long serialVersionUID = 1L;
    @Column(name = "estate_id")
    private Integer estateId;
    @Column(name = "type_id")
    private Integer typeId;
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
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "longitude")
    private Double longitude;
    @Column(name = "latitude")
    private Double latitude;
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
    @Column(name = "process_instance_id")
    private String processInstanceId;
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
    @Column(name = "corp_name")
    private String corpName;
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
    private Date responseTimeOutDate;
    @Column(name = "repair_time_out_date")
    private Date repairTimeOutDate;
    @Column(name = "bike_frame_no")
    private Integer bikeFrameNo;

    @Column(name = "location")
    private String location;

    @Column(name = "bicycle_stake_bar_code")
    private String bicycleStakeBarCode;

    @Column(name = "station_no")
    private String stationNo;

    private Double workOrderScore;

    private Double workOrderScoreDeduct;

    private String scoreDeductRemark;

    @Column(name = "fault_id")
    private Integer faultId;

    @Column(name = "fault_name")
    private  String faultName;

    @Column(name = "check_employee")
    private Integer checkEmployee;

    @Column(name = "check_employee_user_name")
    private String checkEmployeeUserName;

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




    @Basic
    @Column(name = "close_remark")
    public String getCloseRemark() {
        return closeRemark;
    }

    public void setCloseRemark(String closeRemark) {
        this.closeRemark = closeRemark;
    }



    public VwWorkOrder() {
    }


    public Integer getFaultId() {
        return faultId;
    }

    public void setFaultId(Integer faultId) {
        this.faultId = faultId;
    }

    public String getFaultName() {
        return faultName;
    }

    public void setFaultName(String faultName) {
        this.faultName = faultName;
    }

    public Integer getCheckEmployee() {
        return checkEmployee;
    }

    public void setCheckEmployee(Integer checkEmployee) {
        this.checkEmployee = checkEmployee;
    }

    public String getCheckEmployeeUserName() {
        return checkEmployeeUserName;
    }

    public void setCheckEmployeeUserName(String checkEmployeeUserName) {
        this.checkEmployeeUserName = checkEmployeeUserName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public Integer getEstateId() {
        return estateId;
    }

    public void setEstateId(Integer estateId) {
        this.estateId = estateId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
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

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
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

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
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

    public Integer getBikeFrameNo() {
        return bikeFrameNo;
    }

    public void setBikeFrameNo(Integer bikeFrameNo) {
        this.bikeFrameNo = bikeFrameNo;
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

    public String getBicycleStakeBarCode() {
        return bicycleStakeBarCode;
    }

    public void setBicycleStakeBarCode(String bicycleStakeBarCode) {
        this.bicycleStakeBarCode = bicycleStakeBarCode;
    }

    public String getStationNo() {
        return stationNo;
    }

    public void setStationNo(String stationNo) {
        this.stationNo = stationNo;
    }
}
