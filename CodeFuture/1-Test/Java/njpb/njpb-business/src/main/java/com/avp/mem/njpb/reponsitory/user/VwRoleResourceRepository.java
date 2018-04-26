package com.avp.mem.njpb.reponsitory.user;

import com.avp.mem.njpb.entity.VwRoleResource;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Boris.F on 2017/1/11.
 */
public interface VwRoleResourceRepository extends CrudRepository<VwRoleResource,Integer> {

    public List<VwRoleResource> findByRoleId(@Param("roleId") Integer roleId);

}
