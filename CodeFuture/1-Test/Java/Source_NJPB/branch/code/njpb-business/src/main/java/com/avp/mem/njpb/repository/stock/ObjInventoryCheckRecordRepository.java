/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.stock;

import com.avp.mem.njpb.entity.stock.ObjInventoryCheckRecord;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by six on 2017/8/7.
 */
public interface ObjInventoryCheckRecordRepository extends CrudRepository<ObjInventoryCheckRecord, Integer>, JpaSpecificationExecutor<ObjInventoryCheckRecord> {
    @Query(value = "select sum(t.count)  from business.obj_inventory_check_record t where t.corp_id = ?1 and t.param_version =?2", nativeQuery = true)
    Integer findByCorpIdAndParamVersion(@Param("corpId") Integer corpId, @Param("paramVersion") Integer paramVersion);

    @Query(value = "select sum(t.count)  from business.obj_inventory_check_record t where t.check_user_id = ?1 and t.param_version =?2", nativeQuery = true)
    Integer findByCheckUserIdAndParamVersion(@Param("checkUserId") Integer checkUserId, @Param("paramVersion") Integer paramVersion);

    ObjInventoryCheckRecord findTopByCorpIdAndParamVersionLessThanOrderByParamVersionDesc(@Param("corpId") Integer corpId, @Param("paramVersion") Integer paramVersion);

    ObjInventoryCheckRecord findTopByCorpIdOrderByParamVersionDesc(@Param("corpId") Integer corpId);

}
