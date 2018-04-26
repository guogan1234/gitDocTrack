/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.workorder;

import com.avp.mem.njpb.entity.view.VwUserWorkOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by six on 2017/7/28.
 */
public interface VwUserWorkOrderRepository extends CrudRepository<VwUserWorkOrder, Integer>, JpaSpecificationExecutor<VwUserWorkOrder> {

    List<VwUserWorkOrder> findByReportEmployeeAndEstateTypeId(@Param("reportEmployee") Integer reportEmployee, @Param("estateTypeId") Integer estateTypeId);

    Page<VwUserWorkOrder> findByUidAndStatusIdLessThanEqualAndProcessInstanceIdNotInAndLastUpdateTimeLessThanOrderByLastUpdateTimeDesc(@Param("uid") Integer uid, @Param("statusId") Integer statusId, @Param("processInstanceIds") List<String> processInstanceIds, @Param("operationTime") Date operationTime, Pageable page);

    List<VwUserWorkOrder> findByUidAndStatusIdLessThanEqualAndProcessInstanceIdNotInAndLastUpdateTimeGreaterThanOrderByLastUpdateTimeAsc(@Param("uid") Integer uid, @Param("statusId") Integer statusId, @Param("processInstanceIds") List<String> processInstanceIds, @Param("operationTime") Date operationTime);
}
