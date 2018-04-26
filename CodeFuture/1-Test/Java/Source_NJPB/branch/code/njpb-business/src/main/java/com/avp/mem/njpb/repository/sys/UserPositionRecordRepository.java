/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.sys;

import com.avp.mem.njpb.entity.system.SysUserPositionRecord;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Amber on 2017/9/19.
 */
public interface UserPositionRecordRepository extends JpaRepository<SysUserPositionRecord, Integer> {

}
