/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.stock;

import com.avp.mem.njpb.entity.stock.ObjInventoryRecord;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by six on 2017/8/8.
 */
public interface ObjInventoryRecordRepository extends CrudRepository<ObjInventoryRecord, Integer>, JpaSpecificationExecutor<ObjInventoryRecord> {

    List<ObjInventoryRecord> findByOperator(@Param("operator") Integer operator);
}
