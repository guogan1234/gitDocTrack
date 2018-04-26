/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.workorder;

import com.avp.mem.njpb.entity.view.VwWorkOrderModuleBadComponentCount;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by len on 2017/11/9.
 */
public interface VwWorkOrderModuleBadComponentCountRepository extends JpaRepository<VwWorkOrderModuleBadComponentCount, Integer>, JpaSpecificationExecutor<VwWorkOrderModuleBadComponentCount> {


    List<VwWorkOrderModuleBadComponentCount> findByWorkOrderId(@Param("workOrderId")Integer workOrderId);


}
