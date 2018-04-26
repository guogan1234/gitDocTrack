/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.sys;

import com.avp.mem.njpb.entity.view.VwUserPositionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by Amber on 2017/9/19.
 */
public interface VwUserPositionRecordRepository extends JpaRepository<VwUserPositionRecord, Integer> {

    @Query(value = "select * from business.vw_user_position_record where id in( select max(id) from business.vw_user_position_record where record_time >=:startDate and record_time <=:endDate group by user_id) and record_time >=:startDate and record_time <=:endDate ", nativeQuery = true)
    List<VwUserPositionRecord> findLastPosition(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(value = "select * from business.vw_user_position_record where id in( select max(id) from business.vw_user_position_record where record_time >=:startDate and record_time <=:endDate group by user_id) and record_time >=:startDate and record_time <=:endDate and corp_id = :corpId", nativeQuery = true)
    List<VwUserPositionRecord> findLastPositionByCorpId(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("corpId") Integer corpId);


    @Query(value = "select * from business.vw_user_position_record where id in( select max(id) from business.vw_user_position_record where record_time >=:startDate and record_time <=:endDate  group by user_id) and record_time >=:startDate and record_time <=:endDate and user_id = :userId", nativeQuery = true)
    VwUserPositionRecord findLastPositionByUserId(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("userId") Integer userId);



    List<VwUserPositionRecord> findByUserIdAndRecordTimeBetweenOrderByRecordTimeDesc(@Param("userId") Integer userId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
