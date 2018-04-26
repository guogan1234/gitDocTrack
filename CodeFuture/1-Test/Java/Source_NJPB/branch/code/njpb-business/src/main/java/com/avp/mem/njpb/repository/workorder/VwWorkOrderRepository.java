/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.workorder;

import com.avp.mem.njpb.entity.view.VwWorkOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by six on 2017/7/28.
 */
public interface VwWorkOrderRepository extends JpaRepository<VwWorkOrder, Integer>, JpaSpecificationExecutor<VwWorkOrder> {

    List<VwWorkOrder> findByReportEmployeeAndEstateTypeId(@Param("reportEmployee") Integer reportEmployee, @Param("estateTypeId") Integer estateTypeId);

    Page<VwWorkOrder> findByProcessInstanceIdInAndLastUpdateTimeLessThanOrderByLastUpdateTimeDesc(@Param("processInstanceIds") List<String> processInstanceIds, @Param("operationTime") Date operationTime, Pageable page);

    List<VwWorkOrder> findByProcessInstanceIdInAndLastUpdateTimeGreaterThanOrderByLastUpdateTimeAsc(@Param("processInstanceIds") List<String> processInstanceIds, @Param("operationTime") Date operationTime);

    //我的报修上拉
    Page<VwWorkOrder> findByReportWayAndReportEmployeeAndTypeIdAndLastUpdateTimeLessThanOrderByLastUpdateTimeDesc(@Param("reportWay") Integer reportWay, @Param("reportEmployee") Integer reportEmployee, @Param("typeId") Integer typeId, @Param("lastUpdateTime") Date lastUpdateTime, Pageable page);

    //我的报修下拉
    List<VwWorkOrder> findByReportWayAndReportEmployeeAndTypeIdAndLastUpdateTimeGreaterThanOrderByLastUpdateTimeDesc(@Param("reportWay") Integer reportWay, @Param("reportEmployee") Integer reportEmployee, @Param("typeId") Integer typeId, @Param("lastUpdateTime") Date lastUpdateTime);

    //我的维修上拉
    Page<VwWorkOrder> findByReportWayAndRepairEmployeeAndTypeIdAndLastUpdateTimeLessThanOrderByLastUpdateTimeDesc(@Param("reportWay") Integer reportWay, @Param("repairEmployee") Integer repairEmployee, @Param("typeId") Integer typeId, @Param("lastUpdateTime") Date lastUpdateTime, Pageable page);

    //我的维修下拉
    List<VwWorkOrder> findByReportWayAndRepairEmployeeAndTypeIdAndLastUpdateTimeGreaterThanOrderByLastUpdateTimeDesc(@Param("reportWay") Integer reportWay, @Param("repairEmployee") Integer repairEmployee, @Param("typeId") Integer typeId, @Param("lastUpdateTime") Date lastUpdateTime);

    //我的派单上拉
    Page<VwWorkOrder> findByReportWayAndAssignEmployeeAndTypeIdAndLastUpdateTimeLessThanOrderByLastUpdateTimeDesc(@Param("reportWay") Integer reportWay, @Param("assignEmployee") Integer assignEmployee, @Param("typeId") Integer typeId, @Param("lastUpdateTime") Date lastUpdateTime, Pageable page);

    //我的派单下拉
    List<VwWorkOrder> findByReportWayAndAssignEmployeeAndTypeIdAndLastUpdateTimeGreaterThanOrderByLastUpdateTimeDesc(@Param("reportWay") Integer reportWay, @Param("assignEmployee") Integer assignEmployee, @Param("typeId") Integer typeId, @Param("lastUpdateTime") Date lastUpdateTime);

    VwWorkOrder findTopByTypeIdAndEstateIdAndStatusIdInAndFixedOrderByReportTimeDesc(@Param("typeId") Integer typeId, @Param("estateId") Integer estateId, @Param("statusId") List<Integer> statusIds, @Param("fixed") boolean fixed);

    //报表-站点设备故障情况统计-子表数据
    List<VwWorkOrder> findByReportWayAndProjectIdAndEstateTypeIdAndReportTimeBetweenOrderByReportTimeDesc(@Param("reportWay") Integer reportWay, @Param("projectId") Integer projectId, @Param("estateTypeId") Integer estateTypeId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);


    List<VwWorkOrder> findByRepairEmployee(@Param("repairEmployee") Integer repairEmployee);

    List<VwWorkOrder> findByRepairEmployeeAndCreateTimeBetween(@Param("repairEmployee") Integer repairEmployee, @Param("date1") Date date1, @Param("date2") Date date2);

    List<VwWorkOrder> findByReportEmployeeAndCreateTimeBetween(@Param("reportEmployee") Integer reportEmployee, @Param("date1") Date date1, @Param("date2") Date date2);

    List<VwWorkOrder> findByAssignEmployeeAndCreateTimeBetween(@Param("assignEmployee") Integer assignEmployee, @Param("date1") Date date1, @Param("date2") Date date2);


    List<VwWorkOrder> findByIdIn(@Param("ids") List<Integer> ids);


    List<VwWorkOrder> findByRepairEmployeeAndStatusIdAndLastUpdateTimeBetweenOrderByLastUpdateTimeDesc(@Param("repairEmployee") Integer repairEmployee,
                                                                                                       @Param("statusId") Integer statusId, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);


    List<VwWorkOrder> findByCreateTimeBetween(@Param("date1") Date date1, @Param("date2") Date date2);

    List<VwWorkOrder> findByCreateTimeBetweenAndProjectId(@Param("date1") Date date1, @Param("date2") Date date2, @Param("projectId") Integer projectId);

//    @Query(value = "select count(id) as estate_id  , project_id   from business.vw_user_work_order   where create_time between (?1,?2) group by project_id" , nativeQuery = true)
//    List<VwWorkOrder> findByCreateTimeBetweenGroupByProjectId(@Param("date1") Date date1, @Param("date2") Date date2);

    List<VwWorkOrder> findByCreateTimeBetweenAndStatusIdGreaterThanEqual(@Param("date1") Date date1, @Param("date2") Date date2, @Param("statusId") Integer statusId);
}
