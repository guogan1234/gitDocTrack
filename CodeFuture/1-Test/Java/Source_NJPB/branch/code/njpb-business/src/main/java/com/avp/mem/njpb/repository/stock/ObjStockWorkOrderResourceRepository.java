/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.stock;

import com.avp.mem.njpb.entity.stock.ObjStockWorkOrderResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by len on 2017/10/30.
 */
public interface ObjStockWorkOrderResourceRepository  extends JpaRepository<ObjStockWorkOrderResource, Integer> {

    List<ObjStockWorkOrderResource> findByStockWorkOrderId(@Param("stockWorkOrderId") Integer stockWorkOrderId);
}
