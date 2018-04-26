package com.avp.mem.njpb.reponsitory.user;


import com.avp.mem.njpb.entity.VwAuthResource;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Boris on 2016/12/28.
 */
public interface VwAuthResourceRepository extends CrudRepository<VwAuthResource, Integer> {

    List<VwAuthResource> findByUserAccountAndResourceTypeId(@Param("userAccount") String userAccount, @Param("resourceTypeId") Integer resourceTypeId);

}
