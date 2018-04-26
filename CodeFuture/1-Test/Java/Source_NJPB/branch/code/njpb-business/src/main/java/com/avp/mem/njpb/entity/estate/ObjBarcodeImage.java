/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.entity.estate;

import com.avp.mem.njpb.api.entity.EntityBase;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.Where;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by six on 2017/7/24.
 */
@Entity
@DynamicUpdate(true)    //不修改全部列
@DynamicInsert(true)    //不插入全部列
@JsonInclude(JsonInclude.Include.NON_NULL)
@Where(clause = "remove_time is null")
@Table(name = "obj_barcode_image", schema = "business")
public class ObjBarcodeImage extends EntityBase {

    private String barCodePath;

    private String barCodeSn;

    private String barCodeMessage;
    private Integer barCodeCategory;
    private Date exportTime;
    private Date activateTime;
    private Integer relation;


    @Basic
    @Column(name = "bar_code_path")
    public String getBarCodePath() {
        return barCodePath;
    }

    public void setBarCodePath(String barCodePath) {
        this.barCodePath = barCodePath;
    }

    @Basic
    @Column(name = "bar_code_sn")
    public String getBarCodeSn() {
        return barCodeSn;
    }

    public void setBarCodeSn(String barCodeSn) {
        this.barCodeSn = barCodeSn;
    }

    @Basic
    @Column(name = "bar_code_message")
    @Generated(GenerationTime.INSERT)
    public String getBarCodeMessage() {
        return barCodeMessage;
    }

    public void setBarCodeMessage(String barCodeMessage) {
        this.barCodeMessage = barCodeMessage;
    }

    @Basic
    @Column(name = "bar_code_category")
    public Integer getBarCodeCategory() {
        return barCodeCategory;
    }

    public void setBarCodeCategory(Integer barCodeCategory) {
        this.barCodeCategory = barCodeCategory;
    }

    @Basic
    @Column(name = "export_time")
    public Date getExportTime() {
        return exportTime;
    }

    public void setExportTime(Date exportTime) {
        this.exportTime = exportTime;
    }

    @Basic
    @Column(name = "activate_time")
    public Date getActivateTime() {
        return activateTime;
    }

    public void setActivateTime(Date activateTime) {
        this.activateTime = activateTime;
    }

    @Basic
    @Column(name = "relation")
    public Integer getRelation() {
        return relation;
    }

    public void setRelation(Integer relation) {
        this.relation = relation;
    }
}
