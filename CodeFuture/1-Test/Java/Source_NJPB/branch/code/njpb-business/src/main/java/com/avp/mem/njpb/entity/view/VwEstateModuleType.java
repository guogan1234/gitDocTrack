/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.entity.view;

import com.avp.mem.njpb.api.entity.EntityBase;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.Where;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by len on 2017/9/5.
 */
@Entity
@Where(clause = "remove_time is null")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "vw_estate_module_type", schema = "business")
public class VwEstateModuleType extends EntityBase {
    private Integer estateTypeId;
    private String estateName;
    private Integer moduleTypeId;
    private String moduleName;
    private Integer partsType;
    private Double workpoints;

    @Basic
    @Column(name = "estate_type_id")
    public Integer getEstateTypeId() {
        return estateTypeId;
    }

    public void setEstateTypeId(Integer estateTypeId) {
        this.estateTypeId = estateTypeId;
    }

    @Basic
    @Column(name = "module_type_id")
    public Integer getModuleTypeId() {
        return moduleTypeId;
    }

    public void setModuleTypeId(Integer moduleTypeId) {
        this.moduleTypeId = moduleTypeId;
    }

    @Basic
    @Column(name = "estate_name")
    public String getEstateName() {
        return estateName;
    }

    public void setEstateName(String estateName) {
        this.estateName = estateName;
    }

    @Basic
    @Column(name = "module_name")
    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    @Basic
    @Column(name = "parts_type")
    public Integer getPartsType() {
        return partsType;
    }

    public void setPartsType(Integer partsType) {
        this.partsType = partsType;
    }

    @Basic
    @Column(name = "workpoints")
    public Double getWorkpoints() {
        return workpoints;
    }

    public void setWorkpoints(Double workpoints) {
        this.workpoints = workpoints;
    }


}
