package com.avp.mems.backstage.repositories.work;

import com.avp.mems.backstage.entity.work.WorkOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by Amber on 2017/5/27.
 */
public interface WorkOrderRepository extends PagingAndSortingRepository<WorkOrder, Long> {

    Page findByTypeIdAndStatusIdInAndLastUpdateTimeLessThan(@Param("typeId") Long typeId,@Param("statusId") List<Long> statusId, @Param("lastUpdateTime") Date lastUpdateTime, Pageable p);

    List findByTypeIdAndStatusIdInAndLastUpdateTimeGreaterThan(@Param("typeId") Long typeId,@Param("statusId") List<Long> statusId,@Param("lastUpdateTime") Date lastUpdateTime);

    List<WorkOrder> findByProjectIdAndLocationIdAndLastUpdateTimeBetween(@Param("projectId") Long projectId,@Param("locationId") Long locationId,@Param("from") Date from,@Param("to") Date to);

    Integer countByProjectIdAndLocationIdAndLastUpdateTimeBetween(@Param("projectId") Long projectId,@Param("locationId") Long locationId,@Param("from") Date from,@Param("to") Date to);
}
