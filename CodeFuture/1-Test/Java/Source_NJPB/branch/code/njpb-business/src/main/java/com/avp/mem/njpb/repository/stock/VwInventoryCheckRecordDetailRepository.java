/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.stock;

import com.avp.mem.njpb.entity.view.VwInventoryCheckRecordDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by six on 2017/8/8.
 */
public interface VwInventoryCheckRecordDetailRepository extends CrudRepository<VwInventoryCheckRecordDetail, Integer>, JpaRepository<VwInventoryCheckRecordDetail, Integer> {

    Page<VwInventoryCheckRecordDetail> findByCheckUserIdAndParamVersionAndLastUpdateTimeLessThan(@Param("checkUserId") Integer checkUserId, @Param("paramVersion") Integer paramVersion, @Param("lastUpdateTime") Date lastUpdateTime, Pageable p);

    List<VwInventoryCheckRecordDetail> findByCheckUserIdAndParamVersionAndLastUpdateTimeGreaterThan(@Param("checkUserId") Integer checkUserId, @Param("paramVersion") Integer paramVersion, @Param("lastUpdateTime") Date lastUpdateTime);

}
