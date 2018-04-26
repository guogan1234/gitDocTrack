/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.sys;

import com.avp.mem.njpb.entity.view.VwRoleResource;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Boris.F on 2017/1/11.
 */
public interface VwRoleResourceRepository extends CrudRepository<VwRoleResource, Integer> {

    List<VwRoleResource> findByRoleId(@Param("roleId") Integer roleId);

    List<VwRoleResource> findByRoleIdIn(@Param("roleIds") List<Integer> roleIds);

}
