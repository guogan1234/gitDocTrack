/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avp.mem.njpb.reponsitory.user;


import com.avp.mem.njpb.entity.SysProject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Amber Wang
 * @date 2016-11-30
 */
public interface SysProjectRepository extends  CrudRepository <SysProject,Integer>{

    List<SysProject> findByRemoveTimeIsNullOrderByLastUpdateTimeDesc();

    //根据项目名称查询项目
    SysProject findOneByProjectNameAndRemoveTimeIsNull(@Param("projectName") String projectName);

    //根据id查询项目
    SysProject findOneByIdAndRemoveTimeIsNull(@Param("id") Integer id);

    List<SysProject> findByIdNotIn(@Param("id") Integer id);
}
