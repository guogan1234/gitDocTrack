/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.stock;

import com.avp.mem.njpb.entity.stock.VwStockRecordPersonal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by six on 2017/8/17.
 */
public interface VwStockRecordPersonalRepository extends CrudRepository<VwStockRecordPersonal, Integer>, JpaRepository<VwStockRecordPersonal, Integer> {

    List<VwStockRecordPersonal> findByUserIdAndPartsType(@Param("userId") Integer userId, @Param("partsType") Integer partsType);
}
