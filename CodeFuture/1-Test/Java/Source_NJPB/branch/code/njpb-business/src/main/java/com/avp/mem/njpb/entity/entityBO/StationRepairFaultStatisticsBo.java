/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.entity.entityBO;

import java.math.BigInteger;

/**
 * Created by len on 2018/1/23.
 */
public class StationRepairFaultStatisticsBo {

    private Integer projectId;
    private Integer estateTypeId;
    private Integer faultTypeId;
    private String corpName;
    private BigInteger count;
    private String estateTypeName;
    private String faultTypeName;

    public StationRepairFaultStatisticsBo() {

    }


    public StationRepairFaultStatisticsBo(Integer projectId, Integer estateTypeId, Integer faultTypeId, String corpName, BigInteger count, String estateTypeName, String faultTypeName) {
        this.projectId = projectId;
        this.estateTypeId = estateTypeId;
        this.faultTypeId = faultTypeId;
        this.corpName = corpName;
        this.count = count;
        this.estateTypeName = estateTypeName;
        this.faultTypeName = faultTypeName;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
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

    public String getEstateTypeName() {
        return estateTypeName;
    }

    public void setEstateTypeName(String estateTypeName) {
        this.estateTypeName = estateTypeName;
    }

    public String getFaultTypeName() {
        return faultTypeName;
    }

    public void setFaultTypeName(String faultTypeName) {
        this.faultTypeName = faultTypeName;
    }
}
