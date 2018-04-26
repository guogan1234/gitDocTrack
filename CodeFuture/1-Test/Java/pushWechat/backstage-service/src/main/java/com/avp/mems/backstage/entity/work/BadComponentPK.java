/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avp.mems.backstage.entity.work;

import lombok.Data;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 *
 * Created by Amber Wang on 2017-05-27
 */
@Data
@Embeddable
public class BadComponentPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "component_id")
    private int componentId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "work_order_id")
    private int workOrderId;

    public int getComponentId() {
        return componentId;
    }

    public void setComponentId(int componentId) {
        this.componentId = componentId;
    }

    public int getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(int workOrderId) {
        this.workOrderId = workOrderId;
    }
}
