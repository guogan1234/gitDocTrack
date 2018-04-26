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
import java.util.Date;

/**
 * Created by six on 2017/8/15.
 */
@Entity
@Where(clause = "remove_time is null")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "vw_stock_work_order", schema = "business")
public class VwStockWorkOrder extends EntityBase {
    private Integer corpId;
    private String corpName;
    private Integer applyUserId;
    private String applyUserName;
    private Integer processUserId;
    private String processUserName;
    private String serialNo;
    private Integer stockWorkOrderTypeId;
    private Integer operationResult;
    private Date applyTime;
    private Integer stockWorkOrderStatusId;
    private String applyRemark;
    private String rejectRemark;
    private Date confirmTime;
    private Date rejectTime;
    private String stockWorkOrderStatusNameCn;

    @Basic
    @Column(name = "corp_id")
    public Integer getCorpId() {
        return corpId;
    }

    public void setCorpId(Integer corpId) {
        this.corpId = corpId;
    }

    @Basic
    @Column(name = "corp_name")
    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    @Basic
    @Column(name = "apply_user_id")
    public Integer getApplyUserId() {
        return applyUserId;
    }

    public void setApplyUserId(Integer applyUserId) {
        this.applyUserId = applyUserId;
    }

    @Basic
    @Column(name = "apply_user_name")
    public String getApplyUserName() {
        return applyUserName;
    }

    public void setApplyUserName(String applyUserName) {
        this.applyUserName = applyUserName;
    }

    @Basic
    @Column(name = "process_user_id")
    public Integer getProcessUserId() {
        return processUserId;
    }

    public void setProcessUserId(Integer processUserId) {
        this.processUserId = processUserId;
    }

    @Basic
    @Column(name = "process_user_name")
    public String getProcessUserName() {
        return processUserName;
    }

    public void setProcessUserName(String processUserName) {
        this.processUserName = processUserName;
    }

    @Basic
    @Column(name = "serial_no")
    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    @Basic
    @Column(name = "stock_work_order_type_id")
    public Integer getStockWorkOrderTypeId() {
        return stockWorkOrderTypeId;
    }

    public void setStockWorkOrderTypeId(Integer stockWorkOrderTypeId) {
        this.stockWorkOrderTypeId = stockWorkOrderTypeId;
    }

    @Basic
    @Column(name = "operation_result")
    public Integer getOperationResult() {
        return operationResult;
    }

    public void setOperationResult(Integer operationResult) {
        this.operationResult = operationResult;
    }

    @Basic
    @Column(name = "apply_time")
    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    @Basic
    @Column(name = "stock_work_order_status_id")
    public Integer getStockWorkOrderStatusId() {
        return stockWorkOrderStatusId;
    }

    public void setStockWorkOrderStatusId(Integer stockWorkOrderStatusId) {
        this.stockWorkOrderStatusId = stockWorkOrderStatusId;
    }

    @Basic
    @Column(name = "apply_remark")
    public String getApplyRemark() {
        return applyRemark;
    }

    public void setApplyRemark(String applyRemark) {
        this.applyRemark = applyRemark;
    }

    @Basic
    @Column(name = "reject_remark")
    public String getRejectRemark() {
        return rejectRemark;
    }

    public void setRejectRemark(String rejectRemark) {
        this.rejectRemark = rejectRemark;
    }

    @Basic
    @Column(name = "confirm_time")
    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    @Basic
    @Column(name = "reject_time")
    public Date getRejectTime() {
        return rejectTime;
    }

    public void setRejectTime(Date rejectTime) {
        this.rejectTime = rejectTime;
    }

    @Column(name = "stock_work_order_status_name_cn")
    public String getStockWorkOrderStatusNameCn() {
        return stockWorkOrderStatusNameCn;
    }

    public void setStockWorkOrderStatusNameCn(String stockWorkOrderStatusNameCn) {
        this.stockWorkOrderStatusNameCn = stockWorkOrderStatusNameCn;
    }
}
