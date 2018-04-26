/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avp.mems.backstage.entity.report;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author ambEr
 */
@Entity
@Table(name = "fault_distribution_line_week")
public class FaultDistributionLineWeek implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "fault_amount")
    private Integer faultAmount;
    @Column(name = "finish_amount")
    private Integer finishAmount;
    @Column(name = "qod_amount")
    private Integer qodAmount;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "response_ratio", precision = 17, scale = 17)
    private Double responseRatio;
    @Column(name = "growth_rate", precision = 17, scale = 17)
    private Double growthRate;
    @Column(name = "location_id")
    private Integer locationId;
    @Column(name = "location_level")
    private Integer locationLevel;
    @Column(name = "week_of_month")
    private Integer weekOfMonth;
    @Column(name = "week_of_year")
    private String weekOfYear;

    public FaultDistributionLineWeek() {
    }

    public FaultDistributionLineWeek(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFaultAmount() {
        return faultAmount;
    }

    public void setFaultAmount(Integer faultAmount) {
        this.faultAmount = faultAmount;
    }

    public Integer getFinishAmount() {
        return finishAmount;
    }

    public void setFinishAmount(Integer finishAmount) {
        this.finishAmount = finishAmount;
    }

    public Integer getQodAmount() {
        return qodAmount;
    }

    public void setQodAmount(Integer qodAmount) {
        this.qodAmount = qodAmount;
    }

    public Double getResponseRatio() {
        return responseRatio;
    }

    public void setResponseRatio(Double responseRatio) {
        this.responseRatio = responseRatio;
    }

    public Double getGrowthRate() {
        return growthRate;
    }

    public void setGrowthRate(Double growthRate) {
        this.growthRate = growthRate;
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
        if (!(object instanceof FaultDistributionLineWeek)) {
            return false;
        }
        FaultDistributionLineWeek other = (FaultDistributionLineWeek) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.mavenproject1.FaultDistributionLineWeek[ id=" + id + " ]";
    }
    
}
