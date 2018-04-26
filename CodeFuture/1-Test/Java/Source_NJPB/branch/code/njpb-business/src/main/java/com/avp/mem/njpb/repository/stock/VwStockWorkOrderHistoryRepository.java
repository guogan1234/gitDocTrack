/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.stock;

import com.avp.mem.njpb.entity.stock.VwStockWorkOrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by six on 2017/8/17.
 */
public interface VwStockWorkOrderHistoryRepository extends CrudRepository<VwStockWorkOrderHistory, Integer>, JpaRepository<VwStockWorkOrderHistory, Integer> {


}
