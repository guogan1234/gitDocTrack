/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.stock;

import com.avp.mem.njpb.entity.stock.VwStockWorkOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by six on 2017/8/15.
 */
public interface VwStockWorkOrderRepository extends CrudRepository<VwStockWorkOrder, Integer>, JpaRepository<VwStockWorkOrder, Integer> {

    Page<VwStockWorkOrder> findByApplyUserIdAndStockWorkOrderTypeIdAndOperationResultAndLastUpdateTimeLessThan(@Param("applyUserId") Integer applyUserId, @Param("stockWorkOrderTypeId") Integer stockWorkOrderTypeId, @Param("operationResult") Integer operationResult, @Param("lastUpdateTime") Date lastUpdateTime, Pageable p);

    List<VwStockWorkOrder> findByApplyUserIdAndStockWorkOrderTypeIdAndOperationResultAndLastUpdateTimeGreaterThan(@Param("applyUserId") Integer applyUserId, @Param("stockWorkOrderTypeId") Integer stockWorkOrderTypeId, @Param("operationResult") Integer operationResult, @Param("lastUpdateTime") Date lastUpdateTime);

    Page<VwStockWorkOrder> findByStockWorkOrderTypeIdAndStockWorkOrderStatusIdInAndCorpIdAndLastUpdateTimeLessThan(@Param("stockWorkOrderTypeId") Integer stockWorkOrderTypeId, @Param("stockWorkOrderStatusId") List<Integer> stockWorkOrderStatusIds, @Param("corpId") Integer corpId, @Param("lastUpdateTime") Date lastUpdateTime, Pageable p);

    List<VwStockWorkOrder> findByStockWorkOrderTypeIdAndStockWorkOrderStatusIdInAndCorpIdAndLastUpdateTimeGreaterThan(@Param("stockWorkOrderTypeId") Integer stockWorkOrderTypeId, @Param("stockWorkOrderStatusId") List<Integer> stockWorkOrderStatusIds, @Param("corpId") Integer corpId, @Param("lastUpdateTime") Date lastUpdateTime);


    Page<VwStockWorkOrder> findByStockWorkOrderTypeIdAndProcessUserIdInAndLastUpdateTimeLessThan(@Param("stockWorkOrderTypeId") Integer stockWorkOrderTypeId, @Param("processUserId") Integer processUserId, @Param("lastUpdateTime") Date lastUpdateTime, Pageable p);

    List<VwStockWorkOrder> findByStockWorkOrderTypeIdAndProcessUserIdInAndLastUpdateTimeGreaterThan(@Param("stockWorkOrderTypeId") Integer stockWorkOrderTypeId, @Param("processUserId") Integer processUserId, @Param("lastUpdateTime") Date lastUpdateTime);

}
