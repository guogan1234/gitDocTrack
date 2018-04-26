package com.avp.mem.njpb.reponsitory.workorder;

import com.avp.mem.njpb.entity.ObjWorkOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

import java.util.List;


import com.avp.mem.njpb.entity.ObjWorkOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


/**
 * Created by Amber on 2016/12/15.
 */
public interface ObjWorkOrderRepository extends CrudRepository<ObjWorkOrder,Integer> {

    List<ObjWorkOrder> findByRemoveTimeIsNullOrderByLastUpdateTimeDesc();

    //根据设备id查询工单信息
    ObjWorkOrder findOneByIdAndRemoveTimeIsNull(@Param("id") Integer id);

}
