/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.entity.entityBO;

/**
 * Created by len on 2018/1/30.
 */
public class StationEstateStockStatisticsBO {

    private Integer corpId;
    private String corpName;
    private Integer estateTypeId;
    private String estateTypeName;
    private Integer lastMonthCount;
    private Integer thisMonthInStockCount;
    private Integer thisMonthOutStockCount;
    private Integer thisMonthStockCount;

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

    public Integer getEstateTypeId() {
        return estateTypeId;
    }

    public void setEstateTypeId(Integer estateTypeId) {
        this.estateTypeId = estateTypeId;
    }

    public String getEstateTypeName() {
        return estateTypeName;
    }

    public void setEstateTypeName(String estateTypeName) {
        this.estateTypeName = estateTypeName;
    }

    public Integer getLastMonthCount() {
        return lastMonthCount;
    }

    public void setLastMonthCount(Integer lastMonthCount) {
        this.lastMonthCount = lastMonthCount;
    }

    public Integer getThisMonthInStockCount() {
        return thisMonthInStockCount;
    }

    public void setThisMonthInStockCount(Integer thisMonthInStockCount) {
        this.thisMonthInStockCount = thisMonthInStockCount;
    }

    public Integer getThisMonthOutStockCount() {
        return thisMonthOutStockCount;
    }

    public void setThisMonthOutStockCount(Integer thisMonthOutStockCount) {
        this.thisMonthOutStockCount = thisMonthOutStockCount;
    }

    public Integer getThisMonthStockCount() {
        return thisMonthStockCount;
    }

    public void setThisMonthStockCount(Integer thisMonthStockCount) {
        this.thisMonthStockCount = thisMonthStockCount;
    }
}
