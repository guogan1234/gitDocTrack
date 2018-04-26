package com.avp.mems.backstage.entity.report;

import java.util.List;

/**
 * Created by zhoujs on 2017/6/23.
 */
public class FaultMonthReportVo {

	private FaultDistributionLineMonthLocationView faultDistributionLineMonthLocationView;

//	private FaultComponentStatisticsMonth faultComponentStatisticsMonth;

	private List<FaultComponentStatisticsMonth> faultComponentStatisticsMonthList;

	public FaultDistributionLineMonthLocationView getFaultDistributionLineMonthLocationView() {
		return faultDistributionLineMonthLocationView;
	}

	public void setFaultDistributionLineMonthLocationView(FaultDistributionLineMonthLocationView faultDistributionLineMonthLocationView) {
		this.faultDistributionLineMonthLocationView = faultDistributionLineMonthLocationView;
	}

//	public FaultComponentStatisticsMonth getFaultComponentStatisticsMonth() {
//		return faultComponentStatisticsMonth;
//	}
//
//	public void setFaultComponentStatisticsMonth(FaultComponentStatisticsMonth faultComponentStatisticsMonth) {
//		this.faultComponentStatisticsMonth = faultComponentStatisticsMonth;
//	}

	public List<FaultComponentStatisticsMonth> getFaultComponentStatisticsMonthList() {
		return faultComponentStatisticsMonthList;
	}

	public void setFaultComponentStatisticsMonthList(List<FaultComponentStatisticsMonth> faultComponentStatisticsMonthList) {
		this.faultComponentStatisticsMonthList = faultComponentStatisticsMonthList;
	}
}
