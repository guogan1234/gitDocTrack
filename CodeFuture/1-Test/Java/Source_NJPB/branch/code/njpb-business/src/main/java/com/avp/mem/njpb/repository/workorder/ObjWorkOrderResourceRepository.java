/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.workorder;

import com.avp.mem.njpb.entity.workorder.ObjWorkOrderResource;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by six on 2017/7/28.
 */
public interface ObjWorkOrderResourceRepository extends CrudRepository<ObjWorkOrderResource, Integer> {

    List<ObjWorkOrderResource> findByWorkOrderId(@Param("workOrderId") Integer workOrderId);

    List<ObjWorkOrderResource> findByWorkOrderIdAndCategory(@Param("workOrderId") Integer workOrderId, @Param("category") Integer category);
}
