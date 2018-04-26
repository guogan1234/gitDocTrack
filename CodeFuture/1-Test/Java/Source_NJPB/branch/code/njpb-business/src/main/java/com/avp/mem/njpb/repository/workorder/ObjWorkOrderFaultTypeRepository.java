/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.workorder;

import com.avp.mem.njpb.entity.workorder.ObjWorkOrderFaultType;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by len on 2018/1/23.
 */
public interface ObjWorkOrderFaultTypeRepository extends CrudRepository<ObjWorkOrderFaultType, Integer> {

     List<ObjWorkOrderFaultType> findByWorkOrderId(@Param("workOrderId") Integer workOrderId);

}
