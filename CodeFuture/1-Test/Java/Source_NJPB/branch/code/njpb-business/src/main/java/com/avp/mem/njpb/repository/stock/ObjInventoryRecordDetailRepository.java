/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.stock;

import com.avp.mem.njpb.entity.stock.ObjInventoryRecordDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by six on 2017/8/8.
 */
public interface ObjInventoryRecordDetailRepository extends JpaRepository<ObjInventoryRecordDetail, Integer> {

    List<ObjInventoryRecordDetail> findByInventoryRecordId(@Param("InventoryRecordId") Integer inventoryRecordId);

    List<ObjInventoryRecordDetail> findByInventoryRecordIdIn(@Param("InventoryRecordIds") List<Integer> inventoryRecordIds);
}
