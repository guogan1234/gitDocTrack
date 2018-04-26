/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.workorder;

import com.avp.mem.njpb.entity.view.VwWorkOrderFaultType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by len on 2018/1/24.
 */
public interface VwWorkOrderFaultTypeRepository extends JpaRepository<VwWorkOrderFaultType, Integer>, JpaSpecificationExecutor<VwWorkOrderFaultType> {





}
