/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.sys;

import com.avp.mem.njpb.entity.system.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by six on 2017/7/21.
 */
public interface SysUserRepository extends JpaRepository<SysUser, Integer> {

    //根据用户id查询用户
    SysUser findOneById(@Param("id") Integer id);

    //根据用户账号查询
    SysUser findOneByUserAccount(@Param("userAccount") String userAccount);

    SysUser findOneByUserAccountAndIdNot(@Param("userAccount") String userAccount, @Param("id") Integer id);

    //用户名查重
    SysUser findOneByUserName(@Param("userName") String userName);

    SysUser findOneByUserNameAndIdNot(@Param("userName") String userName, @Param("id") Integer id);

    List<SysUser> findByCorpId(@Param("corpId") Integer corpId);

    //根据用户ids查询用户
    List<SysUser> findByIdInOrderByLastUpdateTimeDesc(@Param("ids") List<Integer> ids);

    List<SysUser> findByIdInAndCorpId(@Param("ids") List<Integer> ids, @Param("corpId") Integer corpId);

    List<SysUser> findByIdNotInAndCorpIdIn(@Param("ids") List<Integer> ids, @Param("corpIds") List<Integer> corpIds);

    List<SysUser> findByIdGreaterThanAndCorpIdInOrderByCreateTime(@Param("id") Integer id, @Param("corpIds") List<Integer> corpIds);

    List<SysUser> findByIdGreaterThanAndCorpIdInAndIdNotInOrderByCreateTime(@Param("id") Integer id, @Param("corpIds") List<Integer> corpIds, @Param("ids") List<Integer> ids);

}
