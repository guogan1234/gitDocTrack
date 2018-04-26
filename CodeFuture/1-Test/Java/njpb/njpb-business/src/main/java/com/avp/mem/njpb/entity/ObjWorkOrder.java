package com.avp.mem.njpb.entity;

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
@Table(name = "obj_work_order", schema = "bussiness", catalog = "mem-pb-dev")
@Where(clause = "remove_time is null")
public class ObjWorkOrder extends EntityBase {
    private Integer estateId;
    private Integer typeId;
    private Integer serialNo;
    private Integer reportEmployee;
    private String assignEmployee;
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
    private String stationName;
    private Double longitude;
    private Double latitude;
    private Integer level;
    private Integer reponseOverTime;
    private Integer repairOverTime;

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
    @Column(name = "serial_no")
    public Integer getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(Integer serialNo) {
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
    public String getAssignEmployee() {
        return assignEmployee;
    }

    public void setAssignEmployee(String assignEmployee) {
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
    @Column(name = "station_name")
    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
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


}
