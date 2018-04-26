/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.workorder;

import com.avp.mem.njpb.entity.view.VwWorkOrderHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by six on 2017/7/31.
 */
public interface VwWorkOrderHistoryRepository extends CrudRepository<VwWorkOrderHistory, Integer> {
    List<VwWorkOrderHistory> findByWorkOrderIdOrderByCreateTimeDesc(@Param("workOrderId") Integer workOrderId);

    List<VwWorkOrderHistory> findByWorkOrderIdInOrderByCreateTimeAsc(@Param("workOrderIds") List<Integer> workOrderIds);

}
