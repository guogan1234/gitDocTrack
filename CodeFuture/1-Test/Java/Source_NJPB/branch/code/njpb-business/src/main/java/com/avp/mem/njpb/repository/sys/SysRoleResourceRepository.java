/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.sys;

import com.avp.mem.njpb.entity.system.SysRoleResource;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Boris.F on 2016/12/23.
 */
public interface SysRoleResourceRepository extends JpaRepository<SysRoleResource, Integer> {

    List<SysRoleResource> findByRoleId(@Param("roleId") Integer roleId);

}
