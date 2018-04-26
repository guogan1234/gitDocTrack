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
@Table(name = "obj_work_order_bad_component", schema = "business")
public class ObjWorkOrderBadComponent extends EntityBase {
    private Integer workOrderId;
    private Integer estateTypeId;
    private Integer replaceCount;

    @Basic
    @Column(name = "work_order_id")
    public Integer getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(Integer workOrderId) {
        this.workOrderId = workOrderId;
    }


    @Basic
    @Column(name = "estate_id")
    public Integer getEstateTypeId() {
        return estateTypeId;
    }

    public void setEstateTypeId(Integer estateTypeId) {
        this.estateTypeId = estateTypeId;
    }

    @Column(name = "replace_count")
    public Integer getReplaceCount() {
        return replaceCount;
    }

    public void setReplaceCount(Integer replaceCount) {
        this.replaceCount = replaceCount;
    }

    public ObjWorkOrderBadComponent() {
    }

    public ObjWorkOrderBadComponent(Integer workOrderId, Integer estateTypeId) {
        this.workOrderId = workOrderId;
        this.estateTypeId = estateTypeId;
    }

    public ObjWorkOrderBadComponent(Integer workOrderId, Integer estateTypeId, Integer replaceCount) {
        this.workOrderId = workOrderId;
        this.estateTypeId = estateTypeId;
        this.replaceCount = replaceCount;
    }
}
