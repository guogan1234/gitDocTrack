/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.workorder;

import com.avp.mem.njpb.entity.workorder.ObjWorkOrderOperation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * Created by Amber on 2016/12/15.
 */
public interface ObjWorkorderOperationRepository extends CrudRepository<ObjWorkOrderOperation, Integer> {

//    List<ObjWorkOrderOperation> findByRemoveTimeIsNullOrderByLastUpdateTimeDesc();
//
//    //根据设备id查询工单信息
//    ObjWorkOrderOperation findOneByIdAndRemoveTimeIsNull(@Param("id") Integer id);

    //取出工单上次操作的时间
//    @Query(value = "SELECT owod.* FROM obj_work_operate_detail as owod WHERE work_order_id =:workOrderId GROUP BY owod.id,owod.* ORDER BY owod.id DESC limit 1", nativeQuery = true)
//    ObjWorkOrderOperation findObjWorkOperateDetailLastOperationDateTime(@Param("workOrderId") int workOrderId);

    ObjWorkOrderOperation findTopByWorkOrderIdOrderByCreateTimeDesc(@Param("workOrderId") Integer workOrderId);

    List<ObjWorkOrderOperation> findByWorkOrderIdOrderByCreateTimeDesc(@Param("workOrderId") Integer workOrderId);
}
