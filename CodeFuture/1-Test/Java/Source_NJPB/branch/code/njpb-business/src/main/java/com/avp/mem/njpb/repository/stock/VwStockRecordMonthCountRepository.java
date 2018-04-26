/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.stock;

import com.avp.mem.njpb.entity.stock.VwStockRecordMonthCount;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by len on 2018/1/30.
 */
public interface VwStockRecordMonthCountRepository extends CrudRepository<VwStockRecordMonthCount, Integer>, JpaSpecificationExecutor<VwStockRecordMonthCount> {

    List<VwStockRecordMonthCount> findByLastUpdateTimeGreaterThanAndLastUpdateTimeLessThan(@Param("date1") Date date1, @Param("date2") Date date2);

    VwStockRecordMonthCount findByLastUpdateTimeGreaterThanAndLastUpdateTimeLessThanAndEstateTypeIdAndCorpId(@Param("date1") Date date1, @Param("date2") Date date2,
                                                                                                             @Param("estateTypeId") Integer estateTypeId, @Param("corpId") Integer corpId);
}
