/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.basic;

import com.avp.mem.njpb.entity.view.VwEstateModuleType;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by len on 2017/9/5.
 */
public interface VwEstateModuleTypeRepository extends JpaRepository<VwEstateModuleType, Integer> {

    List<VwEstateModuleType> findByPartsType(@Param("partsType") Integer partsType);

    List<VwEstateModuleType> findByEstateTypeId(@Param("estateTypeId") Integer estateTypeId);

}
