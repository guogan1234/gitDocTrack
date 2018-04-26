package com.avp.mem.njpb.oauth.repository;


import com.avp.mem.njpb.oauth.entity.SysUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author Amber Wang
 * @date 2016-11-29
 */
@RepositoryRestResource
public interface SysUserRepository extends CrudRepository<SysUser, Long> {
	SysUser findOneByUserAccount(@Param("userAccount") String userAccount);
}
