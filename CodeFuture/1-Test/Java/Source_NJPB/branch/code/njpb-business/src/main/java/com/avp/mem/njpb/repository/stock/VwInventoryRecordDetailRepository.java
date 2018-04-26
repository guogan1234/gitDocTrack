/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.stock;

import com.avp.mem.njpb.entity.view.VwInventoryRecordDetail;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by six on 2017/8/7.
 */
public interface VwInventoryRecordDetailRepository extends CrudRepository<VwInventoryRecordDetail, Integer>, JpaRepository<VwInventoryRecordDetail, Integer> {

    List<VwInventoryRecordDetail> findByInventoryRecordIdAndCorpId(@Param("inventoryRecordId") Integer inventoryRecordId, @Param("corpId") Integer corpId);

    Page<VwInventoryRecordDetail> findByOperationTypeAndOperatorAndEstateTypeIdAndLastUpdateTimeLessThan(@Param("operationType") Integer operationType, @Param("operator") Integer operator, @Param("estateTypeId") Integer estateTypeId, @Param("lastUpdateTime") Date lastUpdateTime, Pageable p);

    List<VwInventoryRecordDetail> findByOperationTypeAndOperatorAndEstateTypeIdAndLastUpdateTimeGreaterThan(@Param("operationType") Integer operationType, @Param("operationType") Integer operator, @Param("estateTypeId") Integer estateTypeId, @Param("lastUpdateTime") Date lastUpdateTime);

    List<VwInventoryRecordDetail> findByOperatorAndEstateTypeIdAndOperationType(@Param("operator") Integer operator, @Param("estateTypeId") Integer estateTypeId, @Param("operationType") Integer operationType);
}
