package com.avp.mems.backstage.repositories.report;

import com.avp.mems.backstage.entity.report.FaultDistributionLineDay;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by zhoujs on 2017/6/20.
 */
public interface FaultDistributionLineDayRespository extends PagingAndSortingRepository<FaultDistributionLineDay, Integer> {
	List<FaultDistributionLineDay> findByLocationIdAndLocationLevelAndDayDateBetween(@Param("locationId") Integer locationId, @Param("locationLevel") short locationLevel, @Param("from") Date from, @Param("to") Date to);
}
