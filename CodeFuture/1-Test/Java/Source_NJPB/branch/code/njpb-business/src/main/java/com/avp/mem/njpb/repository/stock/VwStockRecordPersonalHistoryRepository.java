/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.stock;

import com.avp.mem.njpb.entity.stock.VwStockWorkOrderHistory;
import com.avp.mem.njpb.entity.view.VwStockRecordPersonalHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Amber on 2017/9/19.
 */
public interface VwStockRecordPersonalHistoryRepository extends CrudRepository<VwStockRecordPersonalHistory, Integer>, JpaRepository<VwStockRecordPersonalHistory, Integer> {

    List<VwStockRecordPersonalHistory> findByEstateTypeIdAndUserIdOrderByOperationTimeDesc(@Param("estateTypeId") Integer estateTypeId, @Param("userId") Integer userId);
}
