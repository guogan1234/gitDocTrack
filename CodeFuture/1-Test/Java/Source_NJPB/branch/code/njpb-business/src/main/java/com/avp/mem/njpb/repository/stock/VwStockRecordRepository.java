/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.stock;

import com.avp.mem.njpb.entity.view.VwStockRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by len on 2017/9/13.
 */
public interface VwStockRecordRepository extends JpaRepository<VwStockRecord, Integer>, JpaSpecificationExecutor<VwStockRecord> {


}
