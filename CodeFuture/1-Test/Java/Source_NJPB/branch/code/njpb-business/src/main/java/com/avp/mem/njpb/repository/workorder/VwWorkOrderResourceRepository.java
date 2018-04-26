/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.workorder;

import com.avp.mem.njpb.entity.view.VwWorkOrderResource;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by six on 2017/7/31.
 */
public interface VwWorkOrderResourceRepository extends CrudRepository<VwWorkOrderResource, Integer> {

    List<VwWorkOrderResource> findByWorkOrderIdAndCategory(@Param("workOrderId") Integer workOrderId, @Param("category") Integer category);

    List<VwWorkOrderResource> findByWorkOrderId(@Param("workOrderId") Integer workOrderId);

}
