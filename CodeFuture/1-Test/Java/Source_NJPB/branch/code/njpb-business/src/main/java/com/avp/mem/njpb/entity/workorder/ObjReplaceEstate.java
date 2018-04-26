/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.entity.workorder;

import com.avp.mem.njpb.api.entity.EntityBase;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.Where;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by six on 2017/7/24.
 */
@Entity
@Where(clause = "remove_time is null")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "obj_replace_estate", schema = "business")
public class ObjReplaceEstate extends EntityBase {
    private Integer badEstateId;
    private Integer workOrderId;
    private Integer goodEstateId;

    @Basic
    @Column(name = "bad_estate_id")
    public Integer getBadEstateId() {
        return badEstateId;
    }

    public void setBadEstateId(Integer badEstateId) {
        this.badEstateId = badEstateId;
    }

    @Basic
    @Column(name = "work_order_id")
    public Integer getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(Integer workOrderId) {
        this.workOrderId = workOrderId;
    }

    @Basic
    @Column(name = "good_estate_id")
    public Integer getGoodEstateId() {
        return goodEstateId;
    }

    public void setGoodEstateId(Integer goodEstateId) {
        this.goodEstateId = goodEstateId;
    }

}
