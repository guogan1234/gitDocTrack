/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.stock;

import com.avp.mem.njpb.entity.stock.VwStockWorkOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by six on 2017/8/15.
 */
public interface VwStockWorkOrderDetailRepository extends JpaRepository<VwStockWorkOrderDetail, Integer>, JpaSpecificationExecutor<VwStockWorkOrderDetail> {

    List<VwStockWorkOrderDetail> findByStockWorkOrderId(@Param("stockWorkOrderId") Integer stockWorkOrderId);

    List<VwStockWorkOrderDetail> findByEstateTypeIdAndCorpIdAndApplyTimeGreaterThanAndApplyTimeLessThanAndStockWorkOrderTypeIdAndStockWorkOrderStatusId(
            @Param("estateTypeId") Integer estateTypeId, @Param("corpId") Integer corpId, @Param("date1") Date date1, @Param("date2") Date date2, @Param("stockWorkOrderTypeId") Integer stockWorkOrderTypeId, @Param("stockWorkOrderStatusId") Integer stockWorkOrderStatusId);
}
