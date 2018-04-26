/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.entity.estate;

import com.avp.mem.njpb.api.entity.EntityBase;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.Where;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by six on 2017/8/11.
 */
@Entity
@Where(clause = "remove_time is null")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "asso_estate_module_type", schema = "business")
public class AssoEstateModuleType extends EntityBase {
    private int estateTypeId;
    private int moduleTypeId;
    private Integer parentId;


    @Basic
    @Column(name = "estate_type_id")
    public int getEstateTypeId() {
        return estateTypeId;
    }

    public void setEstateTypeId(int estateTypeId) {
        this.estateTypeId = estateTypeId;
    }

    @Basic
    @Column(name = "module_type_id")
    public int getModuleTypeId() {
        return moduleTypeId;
    }

    public void setModuleTypeId(int moduleTypeId) {
        this.moduleTypeId = moduleTypeId;
    }


    @Basic
    @Column(name = "parent_id")
    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public AssoEstateModuleType() {

    }

    public AssoEstateModuleType(int estateTypeId, int moduleTypeId) {
        this.estateTypeId = estateTypeId;
        this.moduleTypeId = moduleTypeId;
    }
}
