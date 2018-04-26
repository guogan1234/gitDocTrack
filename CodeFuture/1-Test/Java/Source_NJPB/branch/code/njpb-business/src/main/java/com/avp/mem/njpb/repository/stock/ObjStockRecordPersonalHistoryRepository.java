/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.stock;

import com.avp.mem.njpb.entity.stock.ObjStockRecordPersonalHistory;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by six on 2017/8/16.
 */
public interface ObjStockRecordPersonalHistoryRepository extends CrudRepository<ObjStockRecordPersonalHistory, Integer>, JpaSpecificationExecutor<ObjStockRecordPersonalHistory> {


}
