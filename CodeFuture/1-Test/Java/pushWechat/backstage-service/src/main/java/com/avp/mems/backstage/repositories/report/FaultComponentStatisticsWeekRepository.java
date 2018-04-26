package com.avp.mems.backstage.repositories.report;

import com.avp.mems.backstage.entity.report.FaultComponentStatisticsWeek;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Amber Wang on 2017-06-21 下午 05:39.
 */
public interface FaultComponentStatisticsWeekRepository extends PagingAndSortingRepository<FaultComponentStatisticsWeek, Integer> {

    FaultComponentStatisticsWeek findByWeekOfYearAndLocationIdAndComponentTypeId(@Param("weekOfYear") String weekOfYear, @Param("locationId") Integer locationId, @Param("componentTypeId") Integer componentTypeId);

    List<FaultComponentStatisticsWeek> findByLocationIdAndWeekOfYearOrderByLocationIdAsc(@Param("locationId") Integer locationId, @Param("weekOfYear") String weekOfYear);

}
