/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.report;

import com.avp.mem.njpb.entity.view.VwInventoryCheckRecord;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by six on 2017/8/10.
 */
public interface VwInventoryCheckRecordRepository extends CrudRepository<VwInventoryCheckRecord, Integer>, JpaSpecificationExecutor<VwInventoryCheckRecord> {

}
