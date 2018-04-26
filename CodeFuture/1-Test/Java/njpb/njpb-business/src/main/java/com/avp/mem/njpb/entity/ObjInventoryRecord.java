package com.avp.mem.njpb.entity;

import com.avp.mem.njpb.api.entity.EntityBase;
import org.hibernate.annotations.Where;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by six on 2017/7/24.
 */
@Entity
@Where(clause="remove_time is null")
@Table(name = "obj_inventory_record", schema = "bussiness", catalog = "mem-pb-dev")
public class ObjInventoryRecord extends EntityBase {
    private Integer corpId;
    private Integer estateTypeId;
    private Integer count;
    private Integer operationType;
    private Integer operator;
    private Integer stockId;

    @Basic
    @Column(name = "corp_id")
    public Integer getCorpId() {
        return corpId;
    }

    public void setCorpId(Integer corpId) {
        this.corpId = corpId;
    }

    @Basic
    @Column(name = "estate_type_id")
    public Integer getEstateTypeId() {
        return estateTypeId;
    }

    public void setEstateTypeId(Integer estateTypeId) {
        this.estateTypeId = estateTypeId;
    }

    @Basic
    @Column(name = "count")
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Basic
    @Column(name = "operation_type")
    public Integer getOperationType() {
        return operationType;
    }

    public void setOperationType(Integer operationType) {
        this.operationType = operationType;
    }

    @Basic
    @Column(name = "operator")
    public Integer getOperator() {
        return operator;
    }

    public void setOperator(Integer operator) {
        this.operator = operator;
    }

    @Basic
    @Column(name = "stock_id")
    public Integer getStockId() {
        return stockId;
    }

    public void setStockId(Integer stockId) {
        this.stockId = stockId;
    }


}
