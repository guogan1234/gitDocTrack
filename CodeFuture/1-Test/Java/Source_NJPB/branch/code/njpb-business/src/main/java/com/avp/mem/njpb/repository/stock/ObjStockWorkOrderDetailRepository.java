/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.stock;

import com.avp.mem.njpb.entity.stock.ObjStockWorkOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by six on 2017/8/14.
 */
public interface ObjStockWorkOrderDetailRepository extends JpaRepository<ObjStockWorkOrderDetail, Integer> {

    List<ObjStockWorkOrderDetail> findByStockWorkOrderId(@Param("stockWorkOrderId") Integer stockWorkOrderId);
}
