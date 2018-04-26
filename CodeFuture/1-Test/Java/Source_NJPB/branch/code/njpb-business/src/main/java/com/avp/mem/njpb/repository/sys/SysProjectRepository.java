/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.sys;


import com.avp.mem.njpb.entity.system.SysProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Amber Wang
 * @date 2016-11-30
 */
public interface SysProjectRepository extends JpaRepository<SysProject, Integer> {

    List<SysProject> findByRemoveTimeIsNullOrderByLastUpdateTimeDesc();

    //根据项目名称查询项目
    SysProject findOneByProjectName(@Param("projectName") String projectName);

    //根据id查询项目
    SysProject findOneById(@Param("id") Integer id);

    List<SysProject> findByIdNotIn(@Param("id") Integer id);
}
