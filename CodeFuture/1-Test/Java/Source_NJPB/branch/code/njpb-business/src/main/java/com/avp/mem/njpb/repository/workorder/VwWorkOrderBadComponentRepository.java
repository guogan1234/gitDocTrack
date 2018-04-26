/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.workorder;

import com.avp.mem.njpb.entity.view.VwWorkOrderBadComponent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by six on 2017/7/28.
 */
public interface VwWorkOrderBadComponentRepository extends CrudRepository<VwWorkOrderBadComponent, Integer> {
    List<VwWorkOrderBadComponent> findByWorkOrderId(@Param("workOrderId") Integer workOrderId);
    List<VwWorkOrderBadComponent> findByWorkOrderIdAndReplaceCountIsNotNull(@Param("workOrderId") Integer workOrderId);
}
