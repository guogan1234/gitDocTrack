/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avp.mems.backstage.entity.work;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author ambEr
 */
@Entity
@Table(name = "work_order_component_view")
public class WorkOrderComponentView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "id")
    @Id
    private BigInteger id;
    @Column(name = "serial_no")
    private BigInteger serialNo;
    @Column(name = "equipment_id")
    private BigInteger equipmentId;
    @Column(name = "report_employee", length = 32)
    private String reportEmployee;
    @Column(name = "plan_start_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date planStartTime;
    @Column(name = "plan_end_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date planEndTime;
    @Column(name = "report_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date reportTime;
    @Column(name = "stst_id")
    private BigInteger ststId;
    @Column(name = "enable")
    private Boolean enable;
    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Column(name = "last_update_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdateTime;
    @Column(name = "location_id")
    private BigInteger locationId;
    @Column(name = "project_id")
    private BigInteger projectId;
    @Column(name = "comtype_id")
    private BigInteger comtypeId;
    @Column(name = "com_id")
    private BigInteger comId;
    @Column(name = "com_name", length = 30)
    private String comName;
    @Column(name = "ep_id")
    private BigInteger epId;
    @Column(name = "epname_cn", length = 30)
    private String epnameCn;
    @Column(name = "epname_en", length = 30)
    private String epnameEn;
    @Column(name = "etypename_cn", length = 30)
    private String etypenameCn;
    @Column(name = "location_name", length = 30)
    private String locationName;
    @Column(name = "line_id")
    private BigInteger lineId;
    @Column(name = "line_parent_id")
    private BigInteger lineParentId;
    @Column(name = "loca_parent_name", length = 30)
    private String locaParentName;

    public WorkOrderComponentView() {
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(BigInteger serialNo) {
        this.serialNo = serialNo;
    }

    public BigInteger getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(BigInteger equipmentId) {
        this.equipmentId = equipmentId;
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

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    public BigInteger getStstId() {
        return ststId;
    }

    public void setStstId(BigInteger ststId) {
        this.ststId = ststId;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public BigInteger getLocationId() {
        return locationId;
    }

    public void setLocationId(BigInteger locationId) {
        this.locationId = locationId;
    }

    public BigInteger getProjectId() {
        return projectId;
    }

    public void setProjectId(BigInteger projectId) {
        this.projectId = projectId;
    }

    public BigInteger getComtypeId() {
        return comtypeId;
    }

    public void setComtypeId(BigInteger comtypeId) {
        this.comtypeId = comtypeId;
    }

    public BigInteger getComId() {
        return comId;
    }

    public void setComId(BigInteger comId) {
        this.comId = comId;
    }

    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }

    public BigInteger getEpId() {
        return epId;
    }

    public void setEpId(BigInteger epId) {
        this.epId = epId;
    }

    public String getEpnameCn() {
        return epnameCn;
    }

    public void setEpnameCn(String epnameCn) {
        this.epnameCn = epnameCn;
    }

    public String getEpnameEn() {
        return epnameEn;
    }

    public void setEpnameEn(String epnameEn) {
        this.epnameEn = epnameEn;
    }

    public String getEtypenameCn() {
        return etypenameCn;
    }

    public void setEtypenameCn(String etypenameCn) {
        this.etypenameCn = etypenameCn;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public BigInteger getLineId() {
        return lineId;
    }

    public void setLineId(BigInteger lineId) {
        this.lineId = lineId;
    }

    public BigInteger getLineParentId() {
        return lineParentId;
    }

    public void setLineParentId(BigInteger lineParentId) {
        this.lineParentId = lineParentId;
    }

    public String getLocaParentName() {
        return locaParentName;
    }

    public void setLocaParentName(String locaParentName) {
        this.locaParentName = locaParentName;
    }
    
}
