/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.stock;

import com.avp.mem.njpb.entity.stock.ObjStockRecordMonthCount;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by len on 2018/1/30.
 */
public interface ObjStockRecordMonthCountRepository extends CrudRepository<ObjStockRecordMonthCount, Integer>, JpaSpecificationExecutor<ObjStockRecordMonthCount> {

    ObjStockRecordMonthCount findTopByEstateTypeIdAndCorpIdOrderByLastUpdateTimeDesc(@Param("estateTypeId") Integer estateTypeId, @Param("corpId") Integer corpId);

}
