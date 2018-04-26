/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.sys;

import com.avp.mem.njpb.entity.system.SysResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Boris on 2016/12/28.
 */
public interface SysResourceRepository extends JpaRepository<SysResource, Integer> {

    List<SysResource> findByRemoveTimeIsNullOrderByIdDesc();

    List<SysResource> findByRemoveTimeIsNullOrderByCreateTimeAsc();


    List<SysResource> findByResourceGrade(@Param("resourceGrade") Integer resourceGrade);
}
