/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.entity.stock;

import com.avp.mem.njpb.api.entity.EntityBase;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.Where;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Created by six on 2017/8/15.
 */
@Entity
@Where(clause = "remove_time is null")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "vw_stock_work_order_detail", schema = "business")
public class VwStockWorkOrderDetail extends EntityBase {
    @Column(name = "stock_work_order_id")
    private Integer stockWorkOrderId;
    @Column(name = "estate_type_id")
    private Integer estateTypeId;
    @Column(name = "estate_type_name")
    private String estateTypeName;
    @Column(name = "parts_type")
    private Integer partsType;
    @Column(name = "category")
    private Integer category;
    @Column(name = "count")
    private Integer count;
    @Column(name = "corp_id")
    private Integer corpId;
    @Column(name = "corp_name")
    private String corpName;
    @Column(name = "apply_user_id")
    private Integer applyUserId;
    @Column(name = "apply_user_name")
    private String applyUserName;
    @Column(name = "process_user_id")
    private Integer processUserId;
    @Column(name = "process_user_name")
    private String processUserName;
    @Column(name = "serial_no")
    private String serialNo;
    @Column(name = "stock_work_order_type_id")
    private Integer stockWorkOrderTypeId;
    @Column(name = "stock_work_order_type_name")
    private String stockWorkOrderTypeName;
    @Column(name = "apply_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date applyTime;
    @Column(name = "stock_work_order_status_id")
    private Integer stockWorkOrderStatusId;
    @Column(name = "apply_remark")
    private String applyRemark;
    @Column(name = "reject_remark")
    private String rejectRemark;
    @Column(name = "confirm_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date confirmTime;
    @Column(name = "reject_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rejectTime;

    public VwStockWorkOrderDetail() {
    }


    public Integer getStockWorkOrderId() {
        return stockWorkOrderId;
    }

    public void setStockWorkOrderId(Integer stockWorkOrderId) {
        this.stockWorkOrderId = stockWorkOrderId;
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

    public Integer getPartsType() {
        return partsType;
    }

    public void setPartsType(Integer partsType) {
        this.partsType = partsType;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

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

    public Integer getApplyUserId() {
        return applyUserId;
    }

    public void setApplyUserId(Integer applyUserId) {
        this.applyUserId = applyUserId;
    }

    public String getApplyUserName() {
        return applyUserName;
    }

    public void setApplyUserName(String applyUserName) {
        this.applyUserName = applyUserName;
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

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
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

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Integer getStockWorkOrderStatusId() {
        return stockWorkOrderStatusId;
    }

    public void setStockWorkOrderStatusId(Integer stockWorkOrderStatusId) {
        this.stockWorkOrderStatusId = stockWorkOrderStatusId;
    }

    public String getApplyRemark() {
        return applyRemark;
    }

    public void setApplyRemark(String applyRemark) {
        this.applyRemark = applyRemark;
    }

    public String getRejectRemark() {
        return rejectRemark;
    }

    public void setRejectRemark(String rejectRemark) {
        this.rejectRemark = rejectRemark;
    }

    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    public Date getRejectTime() {
        return rejectTime;
    }

    public void setRejectTime(Date rejectTime) {
        this.rejectTime = rejectTime;
    }

}
