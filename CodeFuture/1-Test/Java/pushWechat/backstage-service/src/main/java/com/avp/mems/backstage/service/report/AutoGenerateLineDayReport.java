package com.avp.mems.backstage.service.report;

import com.avp.mems.backstage.entity.basic.Location;
import com.avp.mems.backstage.entity.report.FaultDistributionLineDay;
import com.avp.mems.backstage.entity.work.WorkOrderView;
import com.avp.mems.backstage.repositories.basic.LocationRepository;
import com.avp.mems.backstage.repositories.report.FaultDistributionLineDayRespository;
import com.avp.mems.backstage.repositories.user.ProjectRepository;
import com.avp.mems.backstage.repositories.work.WorkOrderViewRepository;
import com.avp.mems.backstage.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhoujs on 2017/6/20.
 */
@Component
public class AutoGenerateLineDayReport {
	@Autowired
	private LocationRepository locationRepository;

	@Autowired
	private WorkOrderViewRepository workOrderViewRepository;

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private FaultDistributionLineDayRespository faultDistributionLineDayRespository;

	private static Logger logger = LoggerFactory.getLogger(AutoGenerateLineDayReport.class);

	@Scheduled(cron = "${business.scheduled.daycron}")
	public void executeGenerateDayReport(){
		logger.info("AutoGenerateLineDayReport job started");

		List<BigInteger> lineIds = projectRepository.findLineIds(6L);

		List<Integer> lineIds_Integer = new ArrayList<>();
		Date beforeDayBeginTIme = DateUtil.getBeforeDayBeginTIme();
		Date beforeDayEndTime = DateUtil.getBeforeDayEndTIme();
		for (BigInteger lineId : lineIds) {
			lineIds_Integer.add(lineId.intValue());
		}
		List<Location> locations = locationRepository.findByIdInAndLevelOrderByIdAsc(lineIds_Integer, (short) 0);
		for(Location location : locations){
			FaultDistributionLineDay faultDistributionLineDay = new FaultDistributionLineDay();
			faultDistributionLineDay.setLocationId(location.getId());
			faultDistributionLineDay.setLocationLevel(location.getLevel());
			faultDistributionLineDay.setLocationName(location.getNameCn());
			faultDistributionLineDay.setDayDate(DateUtil.getBeforeDayTIme());

			List<WorkOrderView> workOrderViewList = workOrderViewRepository.findByProjectIdAndLineIdAndLastUpdateTimeBetween(6L,(long)location.getId() , beforeDayBeginTIme, beforeDayEndTime );
			Integer finishCount = 0;
			for (WorkOrderView workOrderview : workOrderViewList){
				Long status = workOrderview.getStatusId();
				if (status > 6 && status != 8) {
					finishCount++;
				}
			}
			faultDistributionLineDay.setFinishAmount(finishCount);
			faultDistributionLineDay.setFaultAmount(workOrderViewList.size());
			faultDistributionLineDayRespository.save(faultDistributionLineDay);
		}

	}


}
