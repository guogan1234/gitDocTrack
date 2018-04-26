/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.sys;

import com.avp.mem.njpb.entity.view.VwUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by six on 2017/8/7.
 */
public interface VwUserRoleRepository extends JpaRepository<VwUserRole, Integer> {

    List<VwUserRole> findByRoleId(@Param("roleId") Integer roleId);

    List<VwUserRole> findByRoleIdAndCorpId(@Param("roleId") Integer roleId, @Param("corpId") Integer corpId);

    List<VwUserRole> findByRoleIdAndCorpIdAndUserIdNot(@Param("roleId") Integer roleId, @Param("corpId") Integer corpId, @Param("userId") Integer userId);

    List<VwUserRole> findByUserIdAndRoleGrade(@Param("userId") Integer userId, @Param("roleGrade") Integer roleGrade);

    List<VwUserRole> findByUserId(@Param("userId") Integer userId);
}
