/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avp.mems.backstage.entity.work;

import lombok.Data;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * Created by Amber Wang on 2017-05-27
 */
@Data
@Entity
@Table(name = "work_order")
public class WorkOrder implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Generated(GenerationTime.INSERT)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "parent_id")
    private Long parentId;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "type_id")
    private Long typeId;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "serial_no")
    private Long serialNo;
    
//    @Basic(optional = false)
//    @NotNull
    @Column(name = "equipment_id")
    private Long equipmentId;
    
    @Column(name = "fault_type_id")
    private Long faultTypeId;
    
    @Column(name = "fault_description_id")
    private Long faultDescriptionId;
    
    @Size(max = 32)
    @Column(name = "report_employee")
    private String reportEmployee;
    
    @Column(name = "plan_start_time", insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date planStartTime;
    
    @Column(name = "plan_end_time", insertable = false)
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
    
    @Column(name = "report_time", insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date reportTime;
    
    @Basic(optional = false)
    @Column(name = "status_id")
    private Long statusId;
    
    @Basic(optional = false)
    @Column(name = "enabled", insertable = false)
    private Boolean enabled;
    
    @Column(name = "creation_time", insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationTime;
    
    @Column(name = "last_update_time", insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdateTime;
    
    @Size(max = 200)
    @Column(name = "description")
    private String description;
    
    @Basic(optional = false)
    @Column(name = "maintenance_mode_id")
    private Long maintenanceModeId;
    
    @Size(max = 32)
    @Column(name = "assign_employee")
    private String assignEmployee;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "location_id")
    private Long locationId;
    
    @Basic(optional = false)
    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "fix_approach_detail")
    private String fixApproachDetail;

    @Column(name = "bad_component_id")
    private Integer badComponentId;

    @Column(name = "is_wechat_workorder")
    private String isWechatWorkorder;

    //TODO FetchType.EAGER 实时加载改为 FetchType.LAZY 懒加载确认
    @JoinColumn(name = "work_order_id", referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public List<Assignment> assignments = new ArrayList<>();
    
    @JoinColumn(name = "work_order_id", referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public List<BadComponent> badComponents = new ArrayList<>();
    
    @JoinColumn(name = "work_order_id", referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public List<FixApproach> fixApproaches = new ArrayList<>();
    
    @JoinColumn(name = "work_order_id", referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public List<SignInImage> signInImages = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
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

    public Long getFaultDescriptionId() {
        return faultDescriptionId;
    }

    public void setFaultDescriptionId(Long faultDescriptionId) {
        this.faultDescriptionId = faultDescriptionId;
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

    public String getAssignEmployee() {
        return assignEmployee;
    }

    public void setAssignEmployee(String assignEmployee) {
        this.assignEmployee = assignEmployee;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getFixApproachDetail() {
        return fixApproachDetail;
    }

    public void setFixApproachDetail(String fixApproachDetail) {
        this.fixApproachDetail = fixApproachDetail;
    }

    public Integer getBadComponentId() {
        return badComponentId;
    }

    public void setBadComponentId(Integer badComponentId) {
        this.badComponentId = badComponentId;
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }

    public List<BadComponent> getBadComponents() {
        return badComponents;
    }

    public void setBadComponents(List<BadComponent> badComponents) {
        this.badComponents = badComponents;
    }

    public List<FixApproach> getFixApproaches() {
        return fixApproaches;
    }

    public void setFixApproaches(List<FixApproach> fixApproaches) {
        this.fixApproaches = fixApproaches;
    }

    public List<SignInImage> getSignInImages() {
        return signInImages;
    }

    public void setSignInImages(List<SignInImage> signInImages) {
        this.signInImages = signInImages;
    }

    public String getIsWechatWorkorder() {
        return isWechatWorkorder;
    }

    public void setIsWechatWorkorder(String isWechatWorkorder) {
        this.isWechatWorkorder = isWechatWorkorder;
    }
}
