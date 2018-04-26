/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avp.mems.backstage.entity.report;

import javax.persistence.*;
import java.io.Serializable;

/**
 *
 * @author ambEr
 */
@Entity
@Table(name = "fault_component_statistics_week")
public class FaultComponentStatisticsWeek implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "fault_count")
    private Integer faultCount;
    @Column(name = "equipment_count")
    private Integer equipmentCount;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "fault_rate", precision = 17, scale = 17)
    private Double faultRate;
    @Column(name = "growth_rate", precision = 17, scale = 17)
    private Double growthRate;
    @Column(name = "componenttypeid")
    private Integer componentTypeId;
    @Column(name = "locationid")
    private Integer locationId;
    @Column(name = "location_level")
    private Integer locationLevel;
    @Column(name = "week_of_month")
    private Integer weekOfMonth;
    @Column(name = "week_of_year", length = 2147483647)
    private String weekOfYear;
    @Column(name = "component_type_name")
    private String componentTypeName;

    public FaultComponentStatisticsWeek() {
    }

    public FaultComponentStatisticsWeek(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFaultCount() {
        return faultCount;
    }

    public void setFaultCount(Integer faultCount) {
        this.faultCount = faultCount;
    }

    public Integer getEquipmentCount() {
        return equipmentCount;
    }

    public void setEquipmentCount(Integer equipmentCount) {
        this.equipmentCount = equipmentCount;
    }

    public Double getFaultRate() {
        return faultRate;
    }

    public void setFaultRate(Double faultRate) {
        this.faultRate = faultRate;
    }

    public Double getGrowthRate() {
        return growthRate;
    }

    public void setGrowthRate(Double growthRate) {
        this.growthRate = growthRate;
    }

    public Integer getComponentTypeId() {
        return componentTypeId;
    }
    public void setComponentTypeId(Integer componentTypeId) {
        this.componentTypeId = componentTypeId;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public Integer getLocationLevel() {
        return locationLevel;
    }

    public void setLocationLevel(Integer locationLevel) {
        this.locationLevel = locationLevel;
    }

    public Integer getWeekOfMonth() {
        return weekOfMonth;
    }

    public void setWeekOfMonth(Integer weekOfMonth) {
        this.weekOfMonth = weekOfMonth;
    }

    public String getWeekOfYear() {
        return weekOfYear;
    }

    public String getComponentTypeName() {
        return componentTypeName;
    }

    public void setComponentTypeName(String componentTypeName) {
        this.componentTypeName = componentTypeName;
    }

    public void setWeekOfYear(String weekOfYear) {
        this.weekOfYear = weekOfYear;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FaultComponentStatisticsWeek)) {
            return false;
        }
        FaultComponentStatisticsWeek other = (FaultComponentStatisticsWeek) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.mavenproject1.FaultComponentStatisticsWeek[ id=" + id + " ]";
    }
    
}
