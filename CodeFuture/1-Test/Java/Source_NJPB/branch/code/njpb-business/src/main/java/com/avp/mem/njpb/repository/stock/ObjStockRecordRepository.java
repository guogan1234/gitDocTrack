/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.stock;

import com.avp.mem.njpb.entity.stock.ObjStockRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by six on 2017/8/14.
 */
public interface ObjStockRecordRepository extends JpaRepository<ObjStockRecord, Integer> {

    ObjStockRecord findByEstateTypeIdAndCorpId(@Param("estateTypeId") Integer estateTypeId, @Param("corpId") Integer corpId);

    List<ObjStockRecord> findByCorpId(@Param("corpId") Integer corpId);
}
