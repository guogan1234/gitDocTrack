/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.basic;

import com.avp.mem.njpb.entity.system.SysMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by six on 2017/7/28.
 */
public interface SysMessageRepository extends JpaRepository<SysMessage, Integer> {


    List<SysMessage> findByStatus(@Param("status") Integer status);

    List<SysMessage> findByIdInOrderByLastUpdateTimeDesc(@Param("ids") List<Integer> ids);

}
