/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.basic;

import com.avp.mem.njpb.entity.basic.SysUserPositionRecordNow;
import com.avp.mem.njpb.entity.view.VwUserPositionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by len on 2018/2/22.
 */
public interface SysUserPositionRecordNowRepository extends JpaRepository<SysUserPositionRecordNow, Integer> {

    SysUserPositionRecordNow   findTopByUserIdOrderByCreateTimeDesc(@Param("userId") Integer userId);


//    @Query(value = "select * from business.sys_user_position_record_now where id in( select max(id) from business.sys_user_position_record_now  group by user_id) ", nativeQuery = true)
//    List<SysUserPositionRecordNow> findLastPosition();
//
//
//    @Query(value = "select * from business.sys_user_position_record_now where id in( select max(id) from business.sys_user_position_record_now  group by user_id) and  corp_id = :corpId", nativeQuery = true)
//    List<SysUserPositionRecordNow> findLastPositionByCorpId( @Param("corpId") Integer corpId);


    @Query(value = "select * from business.sys_user_position_record_now where id in( select max(id) from business.sys_user_position_record_now where create_time >=:startDate and create_time <=:endDate group by user_id) and create_time >=:startDate and create_time <=:endDate ", nativeQuery = true)
    List<SysUserPositionRecordNow> findLastPositionByCreateTime(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(value = "select * from business.sys_user_position_record_now where id in( select max(id) from business.sys_user_position_record_now where create_time >=:startDate and create_time <=:endDate group by user_id) and create_time >=:startDate and create_time <=:endDate and corp_id = :corpId", nativeQuery = true)
    List<SysUserPositionRecordNow> findLastPositionByCorpIdAndCreateTime(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("corpId") Integer corpId);

    List<SysUserPositionRecordNow> findByUserIdAndCreateTimeBetweenOrderByCreateTimeDesc(@Param("userId") Integer userId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

}
