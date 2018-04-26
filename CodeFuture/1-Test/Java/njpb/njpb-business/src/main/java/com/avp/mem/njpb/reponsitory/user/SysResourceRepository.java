package com.avp.mem.njpb.reponsitory.user;

import com.avp.mem.njpb.entity.SysResource;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Boris on 2016/12/28.
 */
public interface SysResourceRepository extends CrudRepository<SysResource, Integer> {

    List<SysResource> findByRemoveTimeIsNullOrderByIdDesc();

    List<SysResource> findByRemoveTimeIsNullOrderByCreateTimeAsc();
}
