/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.entity.view;

import com.avp.mem.njpb.api.entity.EntityBase;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.Where;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author ambEr
 */
@Entity
@Where(clause = "remove_time is null")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "vw_work_order_bad_component", schema = "business")
public class VwWorkOrderBadComponent extends EntityBase implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "work_order_id")
    private Integer workOrderId;
    @Column(name = "estate_type_id")
    private Integer estateTypeId;
    @Column(name = "replace_count")
    private Integer replaceCount;
    @Column(name = "name")
    private String name;
    @Column(name = "create_by_user_name")
    private String createByUserName;
    @Column(name = "last_update_by_user_name")
    private String lastUpdateByUserName;

    public VwWorkOrderBadComponent() {
    }

    public Integer getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(Integer workOrderId) {
        this.workOrderId = workOrderId;
    }

    public Integer getEstateTypeId() {
        return estateTypeId;
    }

    public void setEstateTypeId(Integer estateTypeId) {
        this.estateTypeId = estateTypeId;
    }

    public Integer getReplaceCount() {
        return replaceCount;
    }

    public void setReplaceCount(Integer replaceCount) {
        this.replaceCount = replaceCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateByUserName() {
        return createByUserName;
    }

    public void setCreateByUserName(String createByUserName) {
        this.createByUserName = createByUserName;
    }

    public String getLastUpdateByUserName() {
        return lastUpdateByUserName;
    }

    public void setLastUpdateByUserName(String lastUpdateByUserName) {
        this.lastUpdateByUserName = lastUpdateByUserName;
    }

}
