/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.workorder;

import com.avp.mem.njpb.entity.workorder.ObjWorkOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * Created by Amber on 2016/12/15.
 */
public interface ObjWorkOrderRepository extends CrudRepository<ObjWorkOrder, Integer> {

    List<ObjWorkOrder> findByRemoveTimeIsNullOrderByLastUpdateTimeDesc();

    ObjWorkOrder findByProcessInstanceId(@Param("processInstanceId") String processInstanceId);
    //根据设备id查询工单信息
//    ObjWorkOrder findOneByIdAndRemoveTimeIsNull(@Param("id") Integer id);
    List<ObjWorkOrder> findByIdInOrderByLastUpdateTimeDesc(@Param("ids") List<Integer> ids);

}
