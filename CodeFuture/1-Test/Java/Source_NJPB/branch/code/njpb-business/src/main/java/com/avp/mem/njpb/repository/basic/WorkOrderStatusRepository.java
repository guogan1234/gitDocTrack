/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.basic;

import com.avp.mem.njpb.entity.workorder.RefWorkOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by six on 2017/7/28.
 */
public interface WorkOrderStatusRepository extends JpaRepository<RefWorkOrderStatus, Integer> {

}
