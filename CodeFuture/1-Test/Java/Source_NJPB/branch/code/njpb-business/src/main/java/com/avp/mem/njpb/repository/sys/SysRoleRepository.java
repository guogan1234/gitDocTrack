/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.sys;

import com.avp.mem.njpb.entity.system.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Boris on 2016/12/28.
 */
public interface SysRoleRepository extends JpaRepository<SysRole, Integer> {


    //根据名称查询角色
    SysRole findOneByRoleName(@Param("roleName") String roleName);

    //根据id查询角色
    SysRole findOneById(@Param("id") Integer id);

    //根据角色ids查询角色
    List<SysRole> findByIdInOrderByLastUpdateTimeDesc(@Param("ids") List<Integer> ids);

    List<SysRole> findByRoleGrade(@Param("roleGrade") Integer roleGrade);

    //根据ids排序查询角色
    //SysRoles findTopByIdInAndIsDeleteIsNullOrderByIdDesc(@Param("ids") List<Integer> ids);
}
