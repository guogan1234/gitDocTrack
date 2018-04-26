package com.avp.mem.njpb.reponsitory.user;

import com.avp.mem.njpb.entity.SysRoleResource;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Boris.F on 2016/12/23.
 */
public interface SysRoleResourceRepository extends CrudRepository<SysRoleResource,Integer> {

     List<SysRoleResource> findByRoleIdAndRemoveTimeIsNull(@Param("roleId") Integer roleId);
    
    List<SysRoleResource> findByRemoveTimeIsNull();
}
