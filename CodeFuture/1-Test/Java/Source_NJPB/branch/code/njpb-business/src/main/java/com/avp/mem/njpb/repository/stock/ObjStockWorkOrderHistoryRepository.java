/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.stock;

import com.avp.mem.njpb.entity.stock.ObjStockWorkOrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by six on 2017/8/17.
 */
public interface ObjStockWorkOrderHistoryRepository extends CrudRepository<ObjStockWorkOrderHistory, Integer>, JpaRepository<ObjStockWorkOrderHistory, Integer> {

    ObjStockWorkOrderHistory findTopByStockWorkOrderIdOrderByCreateTimeDesc(@Param("stockWorkOrderId") Integer stockWorkOrderId);

}


