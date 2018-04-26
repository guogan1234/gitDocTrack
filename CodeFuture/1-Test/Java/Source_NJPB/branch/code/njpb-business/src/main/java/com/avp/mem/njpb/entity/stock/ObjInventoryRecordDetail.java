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
 * Created by six on 2017/7/24.
 */
@Entity
@Where(clause = "remove_time is null")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "obj_inventory_record_detail", schema = "business")
public class ObjInventoryRecordDetail extends EntityBase {
    private Integer inventoryRecordId;
    private Integer estateId;

    @Basic
    @Column(name = "inventory_record_id")
    public Integer getInventoryRecordId() {
        return inventoryRecordId;
    }

    public void setInventoryRecordId(Integer inventoryRecordId) {
        this.inventoryRecordId = inventoryRecordId;
    }

    @Basic
    @Column(name = "estate_id")
    public Integer getEstateId() {
        return estateId;
    }

    public void setEstateId(Integer estateId) {
        this.estateId = estateId;
    }

}
