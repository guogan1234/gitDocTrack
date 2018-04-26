package com.avp.mems.backstage.repositories.report;

import com.avp.mems.backstage.entity.report.FaultDistributionLineMonthLocationView;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by zhoujs on 2017/6/20.
 */
public interface FaultDistributionLineMonthLocationViewRepository extends PagingAndSortingRepository<FaultDistributionLineMonthLocationView, Integer> {

	FaultDistributionLineMonthLocationView findByDataOfYearAndLocationId(@Param("dataOfYear") String dataOfYear, @Param("locationId") Integer locationId);

	List<FaultDistributionLineMonthLocationView> findByDataOfYearAndLocationIdOrParentId(@Param("dataOfYear") String dataOfYear, @Param("locationId") Integer locationId, @Param("parentId") Long parentId);
}
