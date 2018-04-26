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
@Table(name = "obj_estate", schema = "bussiness")
public class ObjEstate extends EntityBase {

    private String estateNo;
    private String estateName;
    private Integer stationId;
    private Integer category;
    private Integer estateTypeId;
    private Integer estateStatusId;
    private String estateSn;
    private Integer supplierId;
    private String estateBatch;
    private Integer parentId;
    private Integer logicalId;
    private Integer projectId;
    private String estatePath;



    @Basic
    @Column(name = "estate_no")
    public String getEstateNo() {
        return estateNo;
    }

    public void setEstateNo(String estateNo) {
        this.estateNo = estateNo;
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
    @Column(name = "station_id")
    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    @Basic
    @Column(name = "category")
    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
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
    @Column(name = "estate_status_id")
    public Integer getEstateStatusId() {
        return estateStatusId;
    }

    public void setEstateStatusId(Integer estateStatusId) {
        this.estateStatusId = estateStatusId;
    }

    @Basic
    @Column(name = "estate_sn")
    public String getEstateSn() {
        return estateSn;
    }

    public void setEstateSn(String estateSn) {
        this.estateSn = estateSn;
    }

    @Basic
    @Column(name = "supplier_id")
    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    @Basic
    @Column(name = "estate_batch")
    public String getEstateBatch() {
        return estateBatch;
    }

    public void setEstateBatch(String estateBatch) {
        this.estateBatch = estateBatch;
    }

    @Basic
    @Column(name = "parent_id")
    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    @Basic
    @Column(name = "logical_id")
    public Integer getLogicalId() {
        return logicalId;
    }

    public void setLogicalId(Integer logicalId) {
        this.logicalId = logicalId;
    }

    @Basic
    @Column(name = "project_id")
    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    @Basic
    @Column(name = "estate_path")
    public String getEstatePath() {
        return estatePath;
    }

    public void setEstatePath(String estatePath) {
        this.estatePath = estatePath;
    }


}
