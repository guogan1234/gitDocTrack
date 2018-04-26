package com.avp.mems.backstage.resource.report;

import com.avp.mems.backstage.entity.report.FaultComponentStatisticsMonth;
import com.avp.mems.backstage.entity.report.FaultDistributionLineDay;
import com.avp.mems.backstage.entity.report.FaultDistributionLineMonthLocationView;
import com.avp.mems.backstage.entity.report.FaultMonthReportVo;
import com.avp.mems.backstage.repositories.report.FaultComponentStatisticsMonthRepository;
import com.avp.mems.backstage.repositories.report.FaultDistributionLineDayRespository;
import com.avp.mems.backstage.repositories.report.FaultDistributionLineMonthLocationViewRepository;
import com.avp.mems.backstage.repositories.user.ProjectRepository;
import com.avp.mems.backstage.rest.ResponseBuilder;
import com.avp.mems.backstage.rest.ResponseCode;
import com.avp.mems.backstage.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by zhoujs on 2017/6/23.
 */
@RestController
public class FaultDistributionLineMonthController {
	@Autowired
	private FaultDistributionLineMonthLocationViewRepository faultDistributionLineMonthLocationViewRepository;

	@Autowired
	private FaultDistributionLineDayRespository faultDistributionLineDayRespository;

	@Autowired
	private FaultComponentStatisticsMonthRepository faultComponentStatisticsMonthRepository;

	@Autowired
	private ProjectRepository projectRepository;

	@RequestMapping(value = "/lineFaultMonth/getLastMonthReport")
	ResponseEntity getFaultMonthReport(@Param("projectId") Long projectId){
		ResponseBuilder builder = ResponseBuilder.createBuilder();
		List<BigInteger> lineIds = projectRepository.findLineIds(projectId);
		List<FaultMonthReportVo>  peojectFaultMonthReportList = new ArrayList<FaultMonthReportVo>();
		for (BigInteger lineId : lineIds) {
		FaultMonthReportVo faultMonthReort = new FaultMonthReportVo();
		Date lastMonthBeginTime = DateUtil.getLastMonthBeginTime(new Date());
		Date lastMonthEndTime = DateUtil.getLastMonthEndTime(new Date());

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(lastMonthBeginTime);
		int month_last_month = calendar.get(Calendar.MONTH)+1;
		int year_last_month = calendar.get(Calendar.YEAR);
		String yearMonthStr = year_last_month +"-"+month_last_month;
		FaultDistributionLineMonthLocationView faultLineMonthReport = faultDistributionLineMonthLocationViewRepository.findByDataOfYearAndLocationId(yearMonthStr,lineId.intValue());

		int weekOfMonth = DateUtil.getWeekOfMonth(new Date());
		List<FaultDistributionLineDay> faultLineDayList = new ArrayList<FaultDistributionLineDay>();
		faultLineDayList = faultDistributionLineDayRespository.findByLocationIdAndLocationLevelAndDayDateBetween(lineId.intValue(), (short) 0, lastMonthBeginTime, lastMonthEndTime);
		if(faultLineDayList.size()>0){
			faultLineMonthReport.setFaultDistributionLineDays(faultLineDayList);
		}
		faultMonthReort.setFaultDistributionLineMonthLocationView(faultLineMonthReport);
		String yearMonth = year_last_month +"."+month_last_month;
		//月_线路_设备 故障统计
//		FaultComponentStatisticsMonth faultComponentStatisticsMonth = faultComponentStatisticsMonthRepository.findByDataOfYearAndLocationIdAndComponentTypeId(yearMonth,lineId,-1);
//		faultMonthReort.setFaultComponentStatisticsMonth(faultComponentStatisticsMonth);
		List<FaultComponentStatisticsMonth> faultComponentStatisticsMonthList = new ArrayList<FaultComponentStatisticsMonth>();
		faultComponentStatisticsMonthList = faultComponentStatisticsMonthRepository.findByLocationIdAndDataOfYearOrderByLocationIdAsc(lineId.intValue(),yearMonth);
		faultMonthReort.setFaultComponentStatisticsMonthList(faultComponentStatisticsMonthList);
			peojectFaultMonthReportList.add(faultMonthReort);
		}
		builder.setResultEntity(peojectFaultMonthReportList, ResponseCode.RETRIEVE_SUCCEED);
		return builder.getResponseEntity();
	}

	@RequestMapping(value = "/lineFaultMonth/getLastMonthComponentDetail")
	ResponseEntity getLastMonthConponentFaultDetail(@Param("lineId") Integer lineId){
		ResponseBuilder builder = ResponseBuilder.createBuilder();
		Date lastMonthBeginTime = DateUtil.getLastMonthBeginTime(new Date());
		Date lastMonthEndTime = DateUtil.getLastMonthEndTime(new Date());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(lastMonthBeginTime);
		int month_last_month = calendar.get(Calendar.MONTH)+1;
		int year_last_month = calendar.get(Calendar.YEAR);
		String yearMonthStr = year_last_month +"."+month_last_month;
		List<FaultComponentStatisticsMonth> componentMonthFaultList = new ArrayList<FaultComponentStatisticsMonth>();
		componentMonthFaultList = faultComponentStatisticsMonthRepository.findByLocationIdAndDataOfYearOrderByLocationIdAsc(lineId,yearMonthStr);
		builder.setResultEntity(componentMonthFaultList,ResponseCode.RETRIEVE_SUCCEED);
		return  builder.getResponseEntity();
	}

	@RequestMapping(value = "/linefaultMonth/getLastMonthFaultLineDetails", method = RequestMethod.GET)
	ResponseEntity getFaultLineDetail(@Param("lineId") Integer lineId) {
		ResponseBuilder builder = ResponseBuilder.createBuilder();
		Date lastMonthBeginTime = DateUtil.getLastMonthBeginTime(new Date());
		Date lastMonthEndTime = DateUtil.getLastMonthEndTime(new Date());

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(lastMonthBeginTime);
		int lastmonthOfYear = calendar.get(Calendar.MONTH)+1;
		int year = calendar.get(Calendar.YEAR);
		String dateStr = year + "-" + lastmonthOfYear;
		List<FaultDistributionLineMonthLocationView> locationMonthFaults = new ArrayList<FaultDistributionLineMonthLocationView>();
		locationMonthFaults = faultDistributionLineMonthLocationViewRepository.findByDataOfYearAndLocationIdOrParentId(dateStr,lineId,lineId.longValue());
		builder.setResultEntity(locationMonthFaults, ResponseCode.RETRIEVE_SUCCEED);
		return builder.getResponseEntity();
	}
}
