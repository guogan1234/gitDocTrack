package com.avp.mem.njpb.reponsitory.user;

import com.avp.mem.njpb.entity.SysUserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Boris.F on 2016/12/23.
 */
public interface SysUserRoleRepository extends CrudRepository<SysUserRole, Integer> {

    List<SysUserRole> findByUserId(@Param("userId") Integer userId);

    //List<SysUserRoles> findByRoleIdAndIsDeleteIsNull(@Param("roleId") Integer roleId);

    //根据角色id查询
    List<SysUserRole> findByRoleId(@Param("roleId") Integer roleId);

    List<SysUserRole> findByRemoveTimeIsNullOrderByLastUpdateTimeDesc();


//    //根据ids查询用户角色数量
//    int countByRoleIdInAndIsDeleteIsNull(@Param("ids") List<Integer> ids);

//    //根据ids查询用户角色
//    SysUserRoles findTopByRoleIdInAndIsDeleteIsNullOrderByIdDesc(@Param("ids") List<Integer> ids);

    //根据角色ids查询用户角色
    //List<SysUserRoles> findByRoleIdInAndIsDeleteIsNull(@Param("ids") List<Integer> ids);

    SysUserRole findTopByRoleIdIn(@Param("roleIds") List<Integer> roleIds);
}
