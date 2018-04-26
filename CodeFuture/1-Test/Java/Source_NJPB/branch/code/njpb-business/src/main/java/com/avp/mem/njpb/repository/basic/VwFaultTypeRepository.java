/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.basic;

import com.avp.mem.njpb.entity.view.VwFaultType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by len on 2018/1/17.
 */
public interface VwFaultTypeRepository extends JpaRepository<VwFaultType, Integer>, JpaSpecificationExecutor<VwFaultType> {

    //根据故障
    VwFaultType findOneByName(@Param("name") String name);

    Integer countByNameAndIdNot(@Param("name") String name,  @Param("id") Integer id);

    //根据ids查询故障
    List<VwFaultType> findByIdIn(@Param("ids") List<Integer> ids);


}
