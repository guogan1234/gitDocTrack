/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.sys;

import com.avp.mem.njpb.entity.view.VwUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by len on 2016/12/15.
 */
public interface VwUserRepository extends JpaRepository<VwUser, Integer> {

    //查询所有
    List<VwUser> findByRemoveTimeIsNullOrderByLastUpdateTimeDesc();

    //    //根据用户名、角色查询
//    List<VwUser> findByRoleIdsAndCorpId(@Param("roleIds") String roleIds, @Param("corpId") Integer corpId);

    List<VwUser> findByIdIn(@Param("id") List<Integer> ids);


    List<VwUser> findByCorpId(@Param("corpId") Integer corpId);

    List<VwUser> findByIdGreaterThanAndCorpIdInAndIdNotInOrderByCreateTime(@Param("id") Integer id, @Param("corpIds") List<Integer> corpIds, @Param("ids") List<Integer> ids);


//    //根据id查询用户信息
//    VwUser findOneByIdAndRemoveTimeIsNull(@Param("id") Integer id);

//    //根据用户名、部门、角色查询
//    List findByRoleIdAndTitleIdAndUserNameLike(@Param("roleId") Integer roleId,@Param("titleId") Integer titleId,@Param("userName") String userName);

//    //根据用户名和部门查询
//    List findByTitleIdAndUserNameLike(@Param("titleId") Integer titleId,@Param("userName") String userName);
//
//    //根据用户名模糊查询
//    List findByUserNameLike(@Param("userName") String userName);

//    //根据部门查询
//    List<VwUser> findByTitleIdAndIsDeleteIsNull(@Param("titleId") Integer titleId);
//
//    //根据userAccount查询对应的角色
//    List<VwUser> findByUserAccountAndIsDeleteIsNull(@Param("userAccount") String userAccount);

    //根据部门ids查询用户
    //List<VwUser> findByTitleIdInAndIsDeleteIsNullAndTitleIdIsNotNullOrderByLastUpdateTimeDesc(@Param("titleIds") List<Integer> titleIds);

    //根据用户id查询用户
    VwUser findById(@Param("id") Integer id);

}
