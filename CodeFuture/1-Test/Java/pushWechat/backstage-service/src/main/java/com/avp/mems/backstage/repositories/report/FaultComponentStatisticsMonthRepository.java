package com.avp.mems.backstage.repositories.report;

import com.avp.mems.backstage.entity.report.FaultComponentStatisticsMonth;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Amber Wang on 2017-06-21 下午 05:39.
 */
public interface FaultComponentStatisticsMonthRepository extends PagingAndSortingRepository<FaultComponentStatisticsMonth, Integer> {

    FaultComponentStatisticsMonth findByDataOfYearAndLocationIdAndComponentTypeId(@Param("dataOfYear") String dataOfYear, @Param("locationId") Integer locationId, @Param("componentTypeId") Integer componentTypeId);

    List<FaultComponentStatisticsMonth> findByLocationIdAndDataOfYearOrderByLocationIdAsc(@Param("locationId") Integer locationId, @Param("dataOfYear") String dataOfYear);

}
