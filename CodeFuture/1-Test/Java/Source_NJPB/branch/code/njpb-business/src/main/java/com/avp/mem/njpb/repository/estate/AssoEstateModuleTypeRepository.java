/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.estate;

import com.avp.mem.njpb.entity.estate.AssoEstateModuleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by six on 2017/8/14.
 */
public interface AssoEstateModuleTypeRepository extends JpaRepository<AssoEstateModuleType, Integer> {

    List<AssoEstateModuleType> findByEstateTypeIdAndModuleTypeId(@Param("estateTypeId") Integer estateTypeId, @Param("moduleTypeId") Integer moduleTypeId);

    List<AssoEstateModuleType> findByEstateTypeId(@Param("estateTypeId") Integer estateTypeId);

    List<AssoEstateModuleType> findByEstateTypeIdIn(@Param("estateTypeIds") List<Integer> estateTypeIds);

    Integer countByModuleTypeId(@Param("moduleTypeId") Integer moduleTypeId);

    List<AssoEstateModuleType> findByModuleTypeId(@Param("moduleTypeId") Integer moduleTypeId);

}
