package com.avp.mems.backstage.entity.report;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by zhoujs on 2017/6/20.
 */
@Entity
@Table(name = "fault_distribution_line_week_location_view")
public class FaultDistributionLineWeekLocationView implements Serializable {
	private static final long serialVersionUID = 2794994812655617741L;
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
	@Column(name = "week_of_month")
	private Integer weekOfMonth;
	@Column(name = "week_of_year")
	private String weekOfYear;
	@Column(name = "parent_id")
	private Long parentId;
	@Column(name = "name_cn")
	private String nameCn;
	@Transient
	private List<FaultDistributionLineDay> faultDistributionLineDays;

	public List<FaultDistributionLineDay> getFaultDistributionLineDays() {
		return faultDistributionLineDays;
	}

	public void setFaultDistributionLineDays(List<FaultDistributionLineDay> faultDistributionLineDays) {
		this.faultDistributionLineDays = faultDistributionLineDays;
	}

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
}
