/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.entity.entityBO;

import java.math.BigInteger;

/**
 * Created by len on 2018/1/24.
 */
public class RepairFaultStatisticsBO {

    private Integer projectId;
    private Integer reportWay;
    private Integer typeId;
    private String corpName;
    private BigInteger count;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getReportWay() {
        return reportWay;
    }

    public void setReportWay(Integer reportWay) {
        this.reportWay = reportWay;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    public BigInteger getCount() {
        return count;
    }

    public void setCount(BigInteger count) {
        this.count = count;
    }
}
