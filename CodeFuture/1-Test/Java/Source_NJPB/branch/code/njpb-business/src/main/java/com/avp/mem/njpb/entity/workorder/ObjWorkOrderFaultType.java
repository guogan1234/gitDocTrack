/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.entity.workorder;

import com.avp.mem.njpb.api.entity.EntityBase;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by len on 2018/1/19.
 */
@Entity
@Where(clause = "remove_time is null")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "obj_work_order_fault_type", schema = "business")
public class ObjWorkOrderFaultType extends EntityBase {

    private Integer workOrderId;
    private Integer faultTypeId;
    private Integer replaceCount;

@Column(name="work_order_id")
    public Integer getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(Integer workOrderId) {
        this.workOrderId = workOrderId;
    }
    @Column(name="fault_type_id")
    public Integer getFaultTypeId() {
        return faultTypeId;
    }

    public void setFaultTypeId(Integer faultTypeId) {
        this.faultTypeId = faultTypeId;
    }
    @Column(name="replace_count")
    public Integer getReplaceCount() {
        return replaceCount;
    }

    public void setReplaceCount(Integer replaceCount) {
        this.replaceCount = replaceCount;
    }
}
