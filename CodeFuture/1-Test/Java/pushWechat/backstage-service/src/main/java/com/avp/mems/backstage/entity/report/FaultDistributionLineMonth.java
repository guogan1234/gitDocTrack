/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avp.mems.backstage.entity.report;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author ambEr
 */
@Entity
@Table(name = "fault_distribution_line_month")
public class FaultDistributionLineMonth implements Serializable {
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
    @Column(name = "month_of_year")
    private Integer monthOfYear;
    @Column(name = "data_of_year")
    private String dataOfYear;

    public FaultDistributionLineMonth() {
    }

    public FaultDistributionLineMonth(Integer id) {
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

    public Integer getMonthOfYear() {
        return monthOfYear;
    }

    public void setMonthOfYear(Integer monthOfYear) {
        this.monthOfYear = monthOfYear;
    }

    public String getDataOfYear() {
        return dataOfYear;
    }

    public void setDataOfYear(String dataOfYear) {
        this.dataOfYear = dataOfYear;
    }
}
