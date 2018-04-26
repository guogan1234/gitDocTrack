package com.avp.mems.backstage.entity.report;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by zhoujs on 2017/6/23.
 */
@Entity
@Table(name = "fault_distribution_line_month_location_view")
public class FaultDistributionLineMonthLocationView implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "faultid", nullable = false)
	private Integer faultId;
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
	@Column(name = "parent_id")
	private Long parentId;
	@Column(name = "name_cn")
	private String nameCn;
	@Transient
	private List<FaultDistributionLineDay> faultDistributionLineDays;

	public Integer getFaultId() {
		return faultId;
	}

	public void setFaultId(Integer faultId) {
		this.faultId = faultId;
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

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getNameCn() {
		return nameCn;
	}

	public void setNameCn(String nameCn) {
		this.nameCn = nameCn;
	}

	public List<FaultDistributionLineDay> getFaultDistributionLineDays() {
		return faultDistributionLineDays;
	}

	public void setFaultDistributionLineDays(List<FaultDistributionLineDay> faultDistributionLineDays) {
		this.faultDistributionLineDays = faultDistributionLineDays;
	}
}
