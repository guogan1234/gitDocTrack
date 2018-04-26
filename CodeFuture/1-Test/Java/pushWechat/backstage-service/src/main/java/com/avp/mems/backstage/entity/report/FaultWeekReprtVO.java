package com.avp.mems.backstage.entity.report;

import java.util.List;

/**
 * Created by zhoujs on 2017/6/23.
 */
public class FaultWeekReprtVO {

	private FaultDistributionLineWeekLocationView faultDistributionLineWeekLocationView;

//	private FaultComponentStatisticsWeek faultComponentStatisticsWeek;

	private List<FaultComponentStatisticsWeek> faultComponentStatisticsWeekList;

	public FaultDistributionLineWeekLocationView getFaultDistributionLineWeekLocationView() {
		return faultDistributionLineWeekLocationView;
	}

	public void setFaultDistributionLineWeekLocationView(FaultDistributionLineWeekLocationView faultDistributionLineWeekLocationView) {
		this.faultDistributionLineWeekLocationView = faultDistributionLineWeekLocationView;
	}

//	public FaultComponentStatisticsWeek getFaultComponentStatisticsWeek() {
//		return faultComponentStatisticsWeek;
//	}
//
//	public void setFaultComponentStatisticsWeek(FaultComponentStatisticsWeek faultComponentStatisticsWeek) {
//		this.faultComponentStatisticsWeek = faultComponentStatisticsWeek;
//	}

	public List<FaultComponentStatisticsWeek> getFaultComponentStatisticsWeekList() {
		return faultComponentStatisticsWeekList;
	}

	public void setFaultComponentStatisticsWeekList(List<FaultComponentStatisticsWeek> faultComponentStatisticsWeekList) {
		this.faultComponentStatisticsWeekList = faultComponentStatisticsWeekList;
	}
}
