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

/**
 * Created by six on 2017/8/14.
 */
@Entity
@Where(clause = "remove_time is null")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "obj_stock_work_order_detail", schema = "business")
public class ObjStockWorkOrderDetail extends EntityBase {
    private Integer stockWorkOrderId;
    private Integer estateTypeId;
    private Integer count;


    @Basic
    @Column(name = "stock_work_order_id")
    public Integer getStockWorkOrderId() {
        return stockWorkOrderId;
    }

    public void setStockWorkOrderId(Integer stockWorkOrderId) {
        this.stockWorkOrderId = stockWorkOrderId;
    }

    @Basic
    @Column(name = "estate_type_id")
    public Integer getEstateTypeId() {
        return estateTypeId;
    }

    public void setEstateTypeId(Integer estateTypeId) {
        this.estateTypeId = estateTypeId;
    }

    @Basic
    @Column(name = "count")
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }


}
