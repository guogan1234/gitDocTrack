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
 * Created by len on 2017/11/1.
 */
@Entity
@Where(clause = "remove_time is null")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "vw_work_order_module_bad_component_count", schema = "business")
public class VwWorkOrderModuleBadComponentCount extends EntityBase {
    private Integer workOrderId;
    private Integer estateTypeId;

    private Integer replaceCount;
    private String name;
    private String createByUserName;
    private String lastUpdateByUserName;
    private Integer estateId;
    private Integer typeId;
    private Integer reportEmployee;
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
    private Integer assignEmployee;
    private Integer reportWay;
    private String serialNo;
    private String processInstanceId;
    private String backRemark;
    private Integer projectId;
    private String estateTypeName;
    private String estateTypeNameEn;
    private Integer estateNo;
    private String estateName;
    private Integer category;
    private String estateSn;
    private String corpName;
    private String reportEmployeeUserName;
    private String assignEmployeeUserName;
    private String repairEmployeeUserName;
    private String workOrderTypeNameCn;
    private String workOrderStatusNameCn;
    private String levelColor;
    private String stationName;
    private String supplierName;
    private Boolean fixed;
    private Date responseTimeOutDate;
    private Date repairTimeOutDate;
    private Integer bikeFrameNo;
    private String location;
    private Double workOrderScore;
    private Double workOrderScoreDeduct;
    private String scoreDeductRemark;
    @Column(name = "station_no")
    private String stationNo;

    @Column(name = "work_order_id")
    public Integer getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(Integer workOrderId) {
        this.workOrderId = workOrderId;
    }

    public String getStationNo() {
        return stationNo;
    }

    public void setStationNo(String stationNo) {
        this.stationNo = stationNo;
    }

    @Basic
    @Column(name = "estate_type_id")
    public Integer getEstateTypeId() {
        return estateTypeId;
    }

    public void setEstateTypeId(Integer estateTypeId) {
        this.estateTypeId = estateTypeId;
    }


    @Basic
    @Column(name = "replace_count")
    public Integer getReplaceCount() {
        return replaceCount;
    }

    public void setReplaceCount(Integer replaceCount) {
        this.replaceCount = replaceCount;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "create_by_user_name")
    public String getCreateByUserName() {
        return createByUserName;
    }

    public void setCreateByUserName(String createByUserName) {
        this.createByUserName = createByUserName;
    }

    @Basic
    @Column(name = "last_update_by_user_name")
    public String getLastUpdateByUserName() {
        return lastUpdateByUserName;
    }

    public void setLastUpdateByUserName(String lastUpdateByUserName) {
        this.lastUpdateByUserName = lastUpdateByUserName;
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
    @Column(name = "report_employee")
    public Integer getReportEmployee() {
        return reportEmployee;
    }

    public void setReportEmployee(Integer reportEmployee) {
        this.reportEmployee = reportEmployee;
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

    @Basic
    @Column(name = "assign_employee")
    public Integer getAssignEmployee() {
        return assignEmployee;
    }

    public void setAssignEmployee(Integer assignEmployee) {
        this.assignEmployee = assignEmployee;
    }

    @Basic
    @Column(name = "report_way")
    public Integer getReportWay() {
        return reportWay;
    }

    public void setReportWay(Integer reportWay) {
        this.reportWay = reportWay;
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
    @Column(name = "process_instance_id")
    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    @Basic
    @Column(name = "back_remark")
    public String getBackRemark() {
        return backRemark;
    }

    public void setBackRemark(String backRemark) {
        this.backRemark = backRemark;
    }

    @Basic
    @Column(name = "project_id")
    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    @Basic
    @Column(name = "estate_type_name")
    public String getEstateTypeName() {
        return estateTypeName;
    }

    public void setEstateTypeName(String estateTypeName) {
        this.estateTypeName = estateTypeName;
    }

    @Basic
    @Column(name = "estate_type_name_en")
    public String getEstateTypeNameEn() {
        return estateTypeNameEn;
    }

    public void setEstateTypeNameEn(String estateTypeNameEn) {
        this.estateTypeNameEn = estateTypeNameEn;
    }

    @Basic
    @Column(name = "estate_no")
    public Integer getEstateNo() {
        return estateNo;
    }

    public void setEstateNo(Integer estateNo) {
        this.estateNo = estateNo;
    }

    @Basic
    @Column(name = "estate_name")
    public String getEstateName() {
        return estateName;
    }

    public void setEstateName(String estateName) {
        this.estateName = estateName;
    }

    @Basic
    @Column(name = "category")
    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    @Basic
    @Column(name = "estate_sn")
    public String getEstateSn() {
        return estateSn;
    }

    public void setEstateSn(String estateSn) {
        this.estateSn = estateSn;
    }

    @Basic
    @Column(name = "corp_name")
    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    @Basic
    @Column(name = "report_employee_user_name")
    public String getReportEmployeeUserName() {
        return reportEmployeeUserName;
    }

    public void setReportEmployeeUserName(String reportEmployeeUserName) {
        this.reportEmployeeUserName = reportEmployeeUserName;
    }

    @Basic
    @Column(name = "assign_employee_user_name")
    public String getAssignEmployeeUserName() {
        return assignEmployeeUserName;
    }

    public void setAssignEmployeeUserName(String assignEmployeeUserName) {
        this.assignEmployeeUserName = assignEmployeeUserName;
    }

    @Basic
    @Column(name = "repair_employee_user_name")
    public String getRepairEmployeeUserName() {
        return repairEmployeeUserName;
    }

    public void setRepairEmployeeUserName(String repairEmployeeUserName) {
        this.repairEmployeeUserName = repairEmployeeUserName;
    }

    @Basic
    @Column(name = "work_order_type_name_cn")
    public String getWorkOrderTypeNameCn() {
        return workOrderTypeNameCn;
    }

    public void setWorkOrderTypeNameCn(String workOrderTypeNameCn) {
        this.workOrderTypeNameCn = workOrderTypeNameCn;
    }

    @Basic
    @Column(name = "work_order_status_name_cn")
    public String getWorkOrderStatusNameCn() {
        return workOrderStatusNameCn;
    }

    public void setWorkOrderStatusNameCn(String workOrderStatusNameCn) {
        this.workOrderStatusNameCn = workOrderStatusNameCn;
    }

    @Basic
    @Column(name = "level_color")
    public String getLevelColor() {
        return levelColor;
    }

    public void setLevelColor(String levelColor) {
        this.levelColor = levelColor;
    }

    @Basic
    @Column(name = "station_name")
    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    @Basic
    @Column(name = "supplier_name")
    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    @Basic
    @Column(name = "fixed")
    public Boolean getFixed() {
        return fixed;
    }

    public void setFixed(Boolean fixed) {
        this.fixed = fixed;
    }

    @Basic
    @Column(name = "response_time_out_date")
    public Date getResponseTimeOutDate() {
        return responseTimeOutDate;
    }

    public void setResponseTimeOutDate(Date responseTimeOutDate) {
        this.responseTimeOutDate = responseTimeOutDate;
    }

    @Basic
    @Column(name = "repair_time_out_date")
    public Date getRepairTimeOutDate() {
        return repairTimeOutDate;
    }

    public void setRepairTimeOutDate(Date repairTimeOutDate) {
        this.repairTimeOutDate = repairTimeOutDate;
    }

    @Basic
    @Column(name = "bike_frame_no")
    public Integer getBikeFrameNo() {
        return bikeFrameNo;
    }

    public void setBikeFrameNo(Integer bikeFrameNo) {
        this.bikeFrameNo = bikeFrameNo;
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
    @Column(name = "score_deduct_remark")
    public String getScoreDeductRemark() {
        return scoreDeductRemark;
    }

    public void setScoreDeductRemark(String scoreDeductRemark) {
        this.scoreDeductRemark = scoreDeductRemark;
    }


}
