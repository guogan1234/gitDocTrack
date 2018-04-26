package com.avp.mem.njpb.reponsitory.user;

import com.avp.mem.njpb.entity.AssoUserProject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Boris.F on 2016/12/23.
 */
public interface AssoUserProjectRepository extends CrudRepository<AssoUserProject,Integer> {

    List<AssoUserProject> findByUserIdOrderByLastUpdateTimeDesc(@Param("userId") Integer userId);
}
