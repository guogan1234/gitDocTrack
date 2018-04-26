/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.basic;

import com.avp.mem.njpb.entity.basic.VwMessageSendDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by six on 2017/8/17.
 */
public interface VwMessageSendDetailRepository extends JpaRepository<VwMessageSendDetail, Integer> {

    Page<VwMessageSendDetail> findByReceiveUserIdAndLastUpdateTimeLessThan(@Param("receiveUserId") Integer receiveUserId, @Param("lastUpdateTime") Date lastUpdateTime, Pageable p);

    List<VwMessageSendDetail> findByReceiveUserIdAndLastUpdateTimeGreaterThan(@Param("receiveUserId") Integer receiveUserId, @Param("lastUpdateTime") Date lastUpdateTime);

    List<VwMessageSendDetail> findBySysMessageId(@Param("sysMessageId") Integer sysMessageId);

}
