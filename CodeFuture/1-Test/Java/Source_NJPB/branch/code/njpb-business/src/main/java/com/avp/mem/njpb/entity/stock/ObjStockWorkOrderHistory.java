/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.entity.stock;

import com.avp.mem.njpb.api.entity.EntityBase;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.Where;

import javax.annotation.Generated;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by six on 2017/8/17.
 */
@Entity
@Where(clause = "remove_time is null")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "obj_stock_work_order_history", schema = "business")
public class ObjStockWorkOrderHistory extends EntityBase {
    private Integer stockWorkOrderId;
    private Integer stockWorkOrderStatusId;
    private Integer processUserId;
    private Date operationTime;
    private Date lastOperationTime;


    @Basic
    @Column(name = "stock_work_order_id")
    public Integer getStockWorkOrderId() {
        return stockWorkOrderId;
    }

    public void setStockWorkOrderId(Integer stockWorkOrderId) {
        this.stockWorkOrderId = stockWorkOrderId;
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
    @Column(name = "process_user_id")
    public Integer getProcessUserId() {
        return processUserId;
    }

    public void setProcessUserId(Integer processUserId) {
        this.processUserId = processUserId;
    }

    @Basic
    @Column(name = "operation_time")
    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }


    @Basic
    @Column(name = "last_operation_time")
    public Date getLastOperationTime() {
        return lastOperationTime;
    }

    public void setLastOperationTime(Date lastOperationTime) {
        this.lastOperationTime = lastOperationTime;
    }


}
