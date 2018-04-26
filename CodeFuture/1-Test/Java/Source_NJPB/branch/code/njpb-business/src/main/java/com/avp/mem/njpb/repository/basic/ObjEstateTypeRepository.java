/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.basic;

import com.avp.mem.njpb.entity.estate.ObjEstateType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Amber Wang on 2017-07-17 下午 06:36.
 */
public interface ObjEstateTypeRepository extends JpaRepository<ObjEstateType, Integer>, JpaSpecificationExecutor<ObjEstateType> {


    //根据设备名查询设备
    ObjEstateType findOneByName(@Param("name") String name);



    Integer countByNameAndCategory(@Param("name") String name, @Param("category") Integer category);

    Integer countByNameAndCategoryAndIdNot(@Param("name") String name, @Param("category") Integer category, @Param("id") Integer id);

    // List<ObjEstateType>  findByEstateTypeId(@Param("estateTypeId") Integer estateTypeId);
    //查询模块
    // List<ObjEstateType>  findByEstateTypeIdIn();

    List<ObjEstateType> findByCategory(@Param("category") Integer category);

    //根据设备ids查询设备类型
    List<ObjEstateType> findByIdIn(@Param("ids") List<Integer> ids);

    List<ObjEstateType> findByPartsType(@Param("partsType") Integer partsType);

    List<ObjEstateType> findByPartsTypeAndCategory(@Param("partsType") Integer partsType, @Param("category") Integer category);
}
