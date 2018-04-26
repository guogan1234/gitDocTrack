/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.entity.entityBO;

import java.math.BigInteger;

/**
 * Created by len on 2018/1/24.
 */
public class WorkOrderFaultTypeBO {
    private Integer id;

    private Integer estateTypeId;

    private Integer faultTypeId;

    private String faultTypeName;


    private Integer reportEmployee;

    private Integer repairEmployee;

    private Integer statusId;

    private Integer stationId;

    private Integer assignEmployee;

    private Integer projectId;
    private String estateTypeName;

    private String corpName;
    private String reportEmployeeUserName;
    private String assignEmployeeUserName;
    private String repairEmployeeUserName;
    private String workOrderStatusNameCn;
    private String stationName;
    private String stationNo;
    private String checkEmployee;
    private String checkEmployeeUserName;
    private BigInteger count;

    private Integer bikeFrameNo;

    public Integer getBikeFrameNo() {
        return bikeFrameNo;
    }

    public void setBikeFrameNo(Integer bikeFrameNo) {
        this.bikeFrameNo = bikeFrameNo;
    }

    public BigInteger getCount() {
        return count;
    }

    public void setCount(BigInteger count) {
        this.count = count;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEstateTypeId() {
        return estateTypeId;
    }

    public void setEstateTypeId(Integer estateTypeId) {
        this.estateTypeId = estateTypeId;
    }

    public Integer getFaultTypeId() {
        return faultTypeId;
    }

    public void setFaultTypeId(Integer faultTypeId) {
        this.faultTypeId = faultTypeId;
    }

    public String getFaultTypeName() {
        return faultTypeName;
    }

    public void setFaultTypeName(String faultTypeName) {
        this.faultTypeName = faultTypeName;
    }

    public Integer getReportEmployee() {
        return reportEmployee;
    }

    public void setReportEmployee(Integer reportEmployee) {
        this.reportEmployee = reportEmployee;
    }

    public Integer getRepairEmployee() {
        return repairEmployee;
    }

    public void setRepairEmployee(Integer repairEmployee) {
        this.repairEmployee = repairEmployee;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public Integer getAssignEmployee() {
        return assignEmployee;
    }

    public void setAssignEmployee(Integer assignEmployee) {
        this.assignEmployee = assignEmployee;
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

    public String getWorkOrderStatusNameCn() {
        return workOrderStatusNameCn;
    }

    public void setWorkOrderStatusNameCn(String workOrderStatusNameCn) {
        this.workOrderStatusNameCn = workOrderStatusNameCn;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStationNo() {
        return stationNo;
    }

    public void setStationNo(String stationNo) {
        this.stationNo = stationNo;
    }

    public String getCheckEmployee() {
        return checkEmployee;
    }

    public void setCheckEmployee(String checkEmployee) {
        this.checkEmployee = checkEmployee;
    }

    public String getCheckEmployeeUserName() {
        return checkEmployeeUserName;
    }

    public void setCheckEmployeeUserName(String checkEmployeeUserName) {
        this.checkEmployeeUserName = checkEmployeeUserName;
    }
}
