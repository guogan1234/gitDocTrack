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
 * Created by six on 2017/8/7.
 */
@Entity
@Where(clause = "remove_time is null")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "obj_inventory_check_record_detail", schema = "business")
public class ObjInventoryCheckRecordDetail extends EntityBase {
    private Integer inventoryCheckRecordId;
    private String estateSn;

    @Basic
    @Column(name = "inventory_check_record_id")
    public Integer getInventoryCheckRecordId() {
        return inventoryCheckRecordId;
    }

    public void setInventoryCheckRecordId(Integer inventoryCheckRecordId) {
        this.inventoryCheckRecordId = inventoryCheckRecordId;
    }

    @Basic
    @Column(name = "estate_sn")
    public String getEstateSn() {
        return estateSn;
    }

    public void setEstateSn(String estateSn) {
        this.estateSn = estateSn;
    }


}
