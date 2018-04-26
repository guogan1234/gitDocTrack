/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.sys;


import com.avp.mem.njpb.entity.view.VwUserRoleResource;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by len on 2017/8/24.
 */
public interface VwUserRoleResourceRepository extends JpaRepository<VwUserRoleResource, Integer> {

    List<VwUserRoleResource> findByUserIdAndResourceType(@Param("userId") Integer userId, @Param("resourceType") Integer resourceType);




}
