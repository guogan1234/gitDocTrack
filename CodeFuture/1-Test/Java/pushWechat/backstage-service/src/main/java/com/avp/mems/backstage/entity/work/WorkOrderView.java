/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avp.mems.backstage.entity.work;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author ambEr
 */
@Entity
@Table(name = "work_order_view")
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class WorkOrderView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "equipment_type_id")
    private Long equipmentTypeId;
    @Column(name = "type_id")
    private Long typeId;
    @Column(name = "serial_no")
    private Long serialNo;
    @Column(name = "equipment_id")
    private Long equipmentId;
    @Column(name = "fault_type_id")
    private Long faultTypeId;
    @Column(name = "report_employee")
    private String reportEmployee;
    @Column(name = "plan_start_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date planStartTime;
    @Column(name = "plan_end_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date planEndTime;
    @Column(name = "plan_fix_approach_id")
    private Long planFixApproachId;
    @Column(name = "plan_budget")
    private Integer planBudget;
    @Column(name = "actual_cost")
    private Integer actualCost;
    @Column(name = "actual_worktime")
    private Integer actualWorktime;
    @Column(name = "report_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date reportTime;
    @Column(name = "status_id")
    private Long statusId;
    @Column(name = "enabled")
    private Boolean enabled;
    @Column(name = "creation_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationTime;
    @Column(name = "last_update_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdateTime;
    @Column(name = "project_id")
    private Long projectId;
    @Column(name = "description")
    private String description;
    @Column(name = "maintenance_mode_id")
    private Long maintenanceModeId;
    @Column(name = "fault_description_id")
    private Long faultDescriptionId;
    @Column(name = "assign_employee")
    private String assignEmployee;
    @Column(name = "repair_employee")
    private String repairEmployee;
    @Column(name = "assign_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date assignTime;
    @Column(name = "repair_way")
    private Integer repairWay;
    @Column(name = "repair_start_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date repairStartTime;
    @Column(name = "repair_end_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date repairEndTime;
    @Column(name = "line_name_cn")
    private String lineNameCn;
    @Column(name = "line_name_en")
    private String lineNameEn;
    @Column(name = "line_id")
    private Long lineId;
    @Column(name = "l_name_cn")
    private String lNameCn;
    @Column(name = "l_name_en")
    private String lNameEn;
    @Column(name = "city_id")
    private Long cityId;
    @Column(name = "device_id")
    private String deviceId;
    @Column(name = "e_name_cn")
    private String eNameCn;
    @Column(name = "e_name_en")
    private String eNameEn;
    @Column(name = "et_name_cn")
    private String etNameCn;
    @Column(name = "et_name_en")
    private String etNameEn;
    @Column(name = "ft_name_cn")
    private String ftNameCn;
    @Column(name = "ft_name_en")
    private String ftNameEn;
    @Column(name = "fd_name_cn")
    private String fdNameCn;
    @Column(name = "fix_approach_detail")
    private String fixApproachDetail;
    @Column(name = "report_employee_name")
    private String reportEmployeeName;

    @Column(name = "assign_employee_name")
    private String assignEmployeeName;

    @Column(name = "bad_component_id")
    private Integer badComponentId;

    @Column(name = "bad_component_name_cn")
    private String badComponentNameCn;

    @Column(name = "repair_employee_name")
    private  String repairEmployeeName;

    @Transient
    private String workOrderTypeValue;

    @Transient
    private String workOrderStatusValue;

    public String getRepairEmployeeName() {
        return repairEmployeeName;
    }

    public void setRepairEmployeeName(String repairEmployeeName) {
        this.repairEmployeeName = repairEmployeeName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEquipmentTypeId() {
        return equipmentTypeId;
    }

    public void setEquipmentTypeId(Long equipmentTypeId) {
        this.equipmentTypeId = equipmentTypeId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Long getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(Long serialNo) {
        this.serialNo = serialNo;
    }

    public Long getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Long equipmentId) {
        this.equipmentId = equipmentId;
    }

    public Long getFaultTypeId() {
        return faultTypeId;
    }

    public void setFaultTypeId(Long faultTypeId) {
        this.faultTypeId = faultTypeId;
    }

    public String getReportEmployee() {
        return reportEmployee;
    }

    public void setReportEmployee(String reportEmployee) {
        this.reportEmployee = reportEmployee;
    }

    public Date getPlanStartTime() {
        return planStartTime;
    }

    public void setPlanStartTime(Date planStartTime) {
        this.planStartTime = planStartTime;
    }

    public Date getPlanEndTime() {
        return planEndTime;
    }

    public void setPlanEndTime(Date planEndTime) {
        this.planEndTime = planEndTime;
    }

    public Long getPlanFixApproachId() {
        return planFixApproachId;
    }

    public void setPlanFixApproachId(Long planFixApproachId) {
        this.planFixApproachId = planFixApproachId;
    }

    public Integer getPlanBudget() {
        return planBudget;
    }

    public void setPlanBudget(Integer planBudget) {
        this.planBudget = planBudget;
    }

    public Integer getActualCost() {
        return actualCost;
    }

    public void setActualCost(Integer actualCost) {
        this.actualCost = actualCost;
    }

    public Integer getActualWorktime() {
        return actualWorktime;
    }

    public void setActualWorktime(Integer actualWorktime) {
        this.actualWorktime = actualWorktime;
    }

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getMaintenanceModeId() {
        return maintenanceModeId;
    }

    public void setMaintenanceModeId(Long maintenanceModeId) {
        this.maintenanceModeId = maintenanceModeId;
    }

    public Long getFaultDescriptionId() {
        return faultDescriptionId;
    }

    public void setFaultDescriptionId(Long faultDescriptionId) {
        this.faultDescriptionId = faultDescriptionId;
    }

    public String getAssignEmployee() {
        return assignEmployee;
    }

    public void setAssignEmployee(String assignEmployee) {
        this.assignEmployee = assignEmployee;
    }

    public String getRepairEmployee() {
        return repairEmployee;
    }

    public void setRepairEmployee(String repairEmployee) {
        this.repairEmployee = repairEmployee;
    }

    public Date getAssignTime() {
        return assignTime;
    }

    public void setAssignTime(Date assignTime) {
        this.assignTime = assignTime;
    }

    public Integer getRepairWay() {
        return repairWay;
    }

    public void setRepairWay(Integer repairWay) {
        this.repairWay = repairWay;
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

    public String getLineNameCn() {
        return lineNameCn;
    }

    public void setLineNameCn(String lineNameCn) {
        this.lineNameCn = lineNameCn;
    }

    public String getLineNameEn() {
        return lineNameEn;
    }

    public void setLineNameEn(String lineNameEn) {
        this.lineNameEn = lineNameEn;
    }

    public Long getLineId() {
        return lineId;
    }

    public void setLineId(Long lineId) {
        this.lineId = lineId;
    }

    public String getlNameCn() {
        return lNameCn;
    }

    public void setlNameCn(String lNameCn) {
        this.lNameCn = lNameCn;
    }

    public String getlNameEn() {
        return lNameEn;
    }

    public void setlNameEn(String lNameEn) {
        this.lNameEn = lNameEn;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String geteNameCn() {
        return eNameCn;
    }

    public void seteNameCn(String eNameCn) {
        this.eNameCn = eNameCn;
    }

    public String geteNameEn() {
        return eNameEn;
    }

    public void seteNameEn(String eNameEn) {
        this.eNameEn = eNameEn;
    }

    public String getEtNameCn() {
        return etNameCn;
    }

    public void setEtNameCn(String etNameCn) {
        this.etNameCn = etNameCn;
    }

    public String getEtNameEn() {
        return etNameEn;
    }

    public void setEtNameEn(String etNameEn) {
        this.etNameEn = etNameEn;
    }

    public String getFtNameCn() {
        return ftNameCn;
    }

    public void setFtNameCn(String ftNameCn) {
        this.ftNameCn = ftNameCn;
    }

    public String getFtNameEn() {
        return ftNameEn;
    }

    public void setFtNameEn(String ftNameEn) {
        this.ftNameEn = ftNameEn;
    }

    public String getFdNameCn() {
        return fdNameCn;
    }

    public void setFdNameCn(String fdNameCn) {
        this.fdNameCn = fdNameCn;
    }

    public Integer getBadComponentId() {
        return badComponentId;
    }

    public void setBadComponentId(Integer badComponentId) {
        this.badComponentId = badComponentId;
    }

    public String getBadComponentNameCn() {
        return badComponentNameCn;
    }

    public void setBadComponentNameCn(String badComponentNameCn) {
        this.badComponentNameCn = badComponentNameCn;
    }

    public String getReportEmployeeName() {
        return reportEmployeeName;
    }

    public void setReportEmployeeName(String reportEmployeeName) {
        this.reportEmployeeName = reportEmployeeName;
    }

    public String getAssignEmployeeName() {
        return assignEmployeeName;
    }

    public void setAssignEmployeeName(String assignEmployeeName) {
        this.assignEmployeeName = assignEmployeeName;
    }

    public String getFixApproachDetail() {
        return fixApproachDetail;
    }

    public void setFixApproachDetail(String fixApproachDetail) {
        this.fixApproachDetail = fixApproachDetail;
    }

    public String getWorkOrderTypeValue() {
        if( getTypeId() == 1){
            this.workOrderTypeValue =  "故障维修工单";
        }
        if(getTypeId() == 2){
            this.workOrderTypeValue = "月度设备保养工单";
        }
        if(getTypeId() == 3){
            this.workOrderTypeValue = "年度设备保养工单";
        }
        if(getTypeId() == 4){
            this.workOrderTypeValue = "企业微信工单";
        }
        return workOrderTypeValue;
    }

    public void setWorkOrderTypeValue(String workOrderTypeValue) {
      this.workOrderTypeValue = workOrderTypeValue;
    }

    public String getWorkOrderStatusValue() {
        if(getStatusId() == 1){
            this.workOrderStatusValue = "工单已创建";
        }else if(getStatusId() == 2){
            this.workOrderStatusValue = "报修已派发";
        }else if(getStatusId() == 3){
            this.workOrderStatusValue = "调度已确认";
        }else if(getStatusId() == 4){
            this.workOrderStatusValue = "调度已派发";
        }else if(getStatusId() == 5){
            this.workOrderStatusValue = "维修已确认";
        }else if(getStatusId() == 6){
            this.workOrderStatusValue = "维修已到达";
        }else if(getStatusId() == 7){
            this.workOrderStatusValue = "维修已完成";
        }else if(getStatusId() == 8){
            this.workOrderStatusValue = "遗留";
        }else if(getStatusId() == 9){
            this.workOrderStatusValue = "工单完成";
        }else if(getStatusId() == 10){
            this.workOrderStatusValue = "工单已已拆分";
        }
        return workOrderStatusValue;
    }

    public void setWorkOrderStatusValue(String workOrderStatusValue) {
       this.workOrderStatusValue = workOrderStatusValue;
    }

    public WorkOrderView(){

    }
}
