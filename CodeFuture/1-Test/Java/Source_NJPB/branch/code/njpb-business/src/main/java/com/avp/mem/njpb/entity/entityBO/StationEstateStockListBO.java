/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.entity.entityBO;

import java.math.BigInteger;

/**
 * Created by len on 2018/2/22.
 */
public class StationEstateStockListBO {
    private Integer corpId;
    private String corpName;
    private Integer estateTypeId;
    private String estateTypeName;
    private BigInteger count;
    private Integer stockWorkOrderTypeId;
    private String stockWorkOrderTypeName;
    private Integer processUserId;
    private String processUserName;


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

    public BigInteger getCount() {
        return count;
    }

    public void setCount(BigInteger count) {
        this.count = count;
    }

    public Integer getStockWorkOrderTypeId() {
        return stockWorkOrderTypeId;
    }

    public void setStockWorkOrderTypeId(Integer stockWorkOrderTypeId) {
        this.stockWorkOrderTypeId = stockWorkOrderTypeId;
    }

    public String getStockWorkOrderTypeName() {
        return stockWorkOrderTypeName;
    }

    public void setStockWorkOrderTypeName(String stockWorkOrderTypeName) {
        this.stockWorkOrderTypeName = stockWorkOrderTypeName;
    }

    public Integer getProcessUserId() {
        return processUserId;
    }

    public void setProcessUserId(Integer processUserId) {
        this.processUserId = processUserId;
    }

    public String getProcessUserName() {
        return processUserName;
    }

    public void setProcessUserName(String processUserName) {
        this.processUserName = processUserName;
    }
}
