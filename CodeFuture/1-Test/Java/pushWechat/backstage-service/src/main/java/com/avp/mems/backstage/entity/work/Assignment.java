/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avp.mems.backstage.entity.work;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * Created by Amber Wang on 2017-05-27
 */
@Data
@Entity
@Table(name = "assignment")
public class Assignment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "work_order_id")
    private Long workOrderId;
    @Size(max = 32)
    @Column(name = "assign_employee")
    private String assignEmployee;
    @Size(max = 32)
    @Column(name = "repair_employee")
    private String repairEmployee;
    @Column(name = "assign_time", insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date assignTime;
    @Column(name = "repair_confirm_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date repairConfirmTime;
    @Column(name = "repair_start_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date repairStartTime;
    @Column(name = "repair_end_time", insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date repairEndTime;
    @Size(max = 200)
    @Column(name = "repair_comment")
    private String repairComment;
    @Column(name = "fixed")
    private Boolean fixed;
    @Column(name = "sign_in_way")
    private Long signInWay;
    @Column(name = "repair_way")
    private Long repairWay;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(Long workOrderId) {
        this.workOrderId = workOrderId;
    }

    public String getAssignEmployee() {
        return assignEmployee;
    }

    public void setAssignEmployee(String assignEmployee) {
        this.assignEmployee = assignEmployee;
    }

    public String getRepairEmployee() {
        return repairEmployee;
    }

    public void setRepairEmployee(String repairEmployee) {
        this.repairEmployee = repairEmployee;
    }

    public Date getAssignTime() {
        return assignTime;
    }

    public void setAssignTime(Date assignTime) {
        this.assignTime = assignTime;
    }

    public Date getRepairConfirmTime() {
        return repairConfirmTime;
    }

    public void setRepairConfirmTime(Date repairConfirmTime) {
        this.repairConfirmTime = repairConfirmTime;
    }

    public Date getRepairStartTime() {
        return repairStartTime;
    }

    public void setRepairStartTime(Date repairStartTime) {
        this.repairStartTime = repairStartTime;
    }

    public Date getRepairEndTime() {
        return repairEndTime;
    }

    public void setRepairEndTime(Date repairEndTime) {
        this.repairEndTime = repairEndTime;
    }

    public String getRepairComment() {
        return repairComment;
    }

    public void setRepairComment(String repairComment) {
        this.repairComment = repairComment;
    }

    public Boolean getFixed() {
        return fixed;
    }

    public void setFixed(Boolean fixed) {
        this.fixed = fixed;
    }

    public Long getSignInWay() {
        return signInWay;
    }

    public void setSignInWay(Long signInWay) {
        this.signInWay = signInWay;
    }

    public Long getRepairWay() {
        return repairWay;
    }

    public void setRepairWay(Long repairWay) {
        this.repairWay = repairWay;
    }
}
