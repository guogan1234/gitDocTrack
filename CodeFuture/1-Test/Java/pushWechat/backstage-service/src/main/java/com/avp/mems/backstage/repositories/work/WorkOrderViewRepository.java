/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avp.mems.backstage.repositories.work;

import com.avp.mems.backstage.entity.work.WorkOrder;
import com.avp.mems.backstage.entity.work.WorkOrderView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Amber
 */
public interface WorkOrderViewRepository extends PagingAndSortingRepository<WorkOrderView, Long> {
    @Query("select w from WorkOrderView w where (w.statusId in(1,2,3,4,5,6) and w.lastUpdateTime<:lastUpdateTime and w.projectId = :projectId) and (w.reportEmployee=:uid or w.assignEmployee=:uid)")
    Page<WorkOrderView> findByLastUpdateTimeLessThanAndProjectId(@Param("uid") String uid,  @Param("lastUpdateTime") Date lastUpdateTime, @Param("projectId") Long projectId, Pageable p);

    @Query("select w from WorkOrderView w where (w.statusId in(1,2,3,4,5,6) and w.lastUpdateTime>:lastUpdateTime and w.projectId = :projectId) and (w.reportEmployee=:uid or w.assignEmployee=:uid)")
    List<WorkOrderView> findByLastUpdateTimeGreaterThanAndProjectId(@Param("uid") String uid,  @Param("lastUpdateTime") Date lastUpdateTime, @Param("projectId") Long projectId);

    List<WorkOrderView> findByProjectIdAndLineIdAndLastUpdateTimeBetween(@Param("projectId") Long projectId, @Param("lineId") Long lineId, @Param("from") Date from,@Param("to") Date to);
}
