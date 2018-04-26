/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.sys;

import com.avp.mem.njpb.entity.system.SysVersionApp;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by len on 2017/10/30.
 */
public interface SysVersionAppRepository extends CrudRepository<SysVersionApp, Integer> {

    SysVersionApp findTopByOrderByCreateTimeDesc();

}
