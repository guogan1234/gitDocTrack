package com.avp.mems.backstage.repositories.work;

import com.avp.mems.backstage.entity.work.WorkOrderComponentView;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * Created by Amber Wang on 2017-06-22 下午 06:25.
 */
public interface WorkOrderComponentViewRepository extends PagingAndSortingRepository<WorkOrderComponentView, BigInteger> {

List<WorkOrderComponentView> findByProjectIdAndLineParentIdAndLastUpdateTimeBetween(@Param("projectId") BigInteger projectId, @Param("lineId") BigInteger lineId, @Param("from") Date from, @Param("to") Date to);
}
