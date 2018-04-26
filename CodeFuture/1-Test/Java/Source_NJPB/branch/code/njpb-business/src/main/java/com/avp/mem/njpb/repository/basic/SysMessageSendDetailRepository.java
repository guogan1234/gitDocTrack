/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.basic;

import com.avp.mem.njpb.entity.basic.SysMessageSendDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by six on 2017/8/17.
 */
public interface SysMessageSendDetailRepository extends JpaRepository<SysMessageSendDetail, Integer> {

    List<SysMessageSendDetail>  findBySysMessageId(@Param("sysMessageId") Integer sysMessageId);
}
