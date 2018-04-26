/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.entity.view;

import com.avp.mem.njpb.api.entity.EntityBase;
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
 *
 * @author ambEr
 */
@Entity
@Where(clause = "remove_time is null")
@Table(name = "vw_stock_record_personal_history", schema = "business")
public class VwStockRecordPersonalHistory extends EntityBase implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "estate_type_id")
    private Integer estateTypeId;
    @Column(name = "count")
    private Integer count;
    @Column(name = "operation_type")
    private Integer operationType;
    @Column(name = "operation_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date operationTime;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "estate_type_name")
    private String estateTypeName;
    @Column(name = "operation_type_name_cn")
    private String operationTypeNameCn;

    public VwStockRecordPersonalHistory() {
    }

    public Integer getEstateTypeId() {
        return estateTypeId;
    }

    public void setEstateTypeId(Integer estateTypeId) {
        this.estateTypeId = estateTypeId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getOperationType() {
        return operationType;
    }

    public void setOperationType(Integer operationType) {
        this.operationType = operationType;
    }

    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEstateTypeName() {
        return estateTypeName;
    }

    public void setEstateTypeName(String estateTypeName) {
        this.estateTypeName = estateTypeName;
    }

    public String getOperationTypeNameCn() {
        return operationTypeNameCn;
    }

    public void setOperationTypeNameCn(String operationTypeNameCn) {
        this.operationTypeNameCn = operationTypeNameCn;
    }
    
}
