package com.avp.mems.backstage.entity.report;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhoujs on 2017/6/20.
 */
@Entity
@Table(name = "fault_distribution_line_day")
public class FaultDistributionLineDay implements Serializable {
	private static final long serialVersionUID = -4913273441512920109L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id", nullable = false)
	private Integer id;
	@Column(name = "fault_amount")
	private Integer faultAmount;
	@Column(name = "finish_amount")
	private Integer finishAmount;
	@Column(name = "location_id")
	private Integer locationId;
	@Column(name = "location_level")
	private short locationLevel;
	@Column(name = "day_date")
	private Date dayDate;
	@Column(name = "location_name")
	private String locationName;

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

	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	public short getLocationLevel() {
		return locationLevel;
	}

	public void setLocationLevel(short locationLevel) {
		this.locationLevel = locationLevel;
	}

	public Date getDayDate() {
		return dayDate;
	}

	public void setDayDate(Date dayDate) {
		this.dayDate = dayDate;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
}
