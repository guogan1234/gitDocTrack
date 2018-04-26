/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.basic;

import com.avp.mem.njpb.entity.estate.ObjFaultType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by len on 2018/1/15.
 */
public interface ObjFaultTypeRepository extends JpaRepository<ObjFaultType, Integer>, JpaSpecificationExecutor<ObjFaultType> {


    //根据故障
    ObjFaultType findOneByName(@Param("name") String name);

    Integer countByNameAndIdNot(@Param("name") String name,  @Param("id") Integer id);

    //根据ids查询故障
    List<ObjFaultType> findByIdIn(@Param("ids") List<Integer> ids);
}
