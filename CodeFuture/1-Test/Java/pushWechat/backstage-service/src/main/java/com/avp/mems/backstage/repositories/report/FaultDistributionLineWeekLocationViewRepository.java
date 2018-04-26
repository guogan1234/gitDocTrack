package com.avp.mems.backstage.repositories.report;

import com.avp.mems.backstage.entity.report.FaultDistributionLineWeekLocationView;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by zhoujs on 2017/6/20.
 */
public interface FaultDistributionLineWeekLocationViewRepository extends PagingAndSortingRepository<FaultDistributionLineWeekLocationView, Integer> {

	FaultDistributionLineWeekLocationView findByWeekOfYearAndLocationId(@Param("weekOfYear") String weekOfYear, @Param("locationId") Integer locationId);

	List<FaultDistributionLineWeekLocationView> findByWeekOfYearAndLocationIdOrParentId(@Param("weekOfYear") String weekOfYear, @Param("locationId") Integer locationId, @Param("parentId") Long parentId);
}
