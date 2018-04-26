/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.stock;

import com.avp.mem.njpb.entity.stock.ObjStockRecordPersonal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by six on 2017/8/14.
 */
public interface ObjStockRecordPersonalRepository extends JpaRepository<ObjStockRecordPersonal, Integer> {

    ObjStockRecordPersonal findByUserIdAndEstateTypeId(@Param("userId") Integer userId, @Param("estateTypeId") Integer estateTypeId);

}
