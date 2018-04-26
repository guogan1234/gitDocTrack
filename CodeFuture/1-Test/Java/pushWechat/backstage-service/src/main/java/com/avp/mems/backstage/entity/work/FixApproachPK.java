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
 * @author peter
 */
@Data
@Embeddable
public class FixApproachPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "fix_approach_id")
    private short fixApproachId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "work_order_id")
    private int workOrderId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fault_type_id")
    private short faultTypeId;
}
