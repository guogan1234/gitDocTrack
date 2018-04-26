package com.avp.mem.njpb.reponsitory.user;

import com.avp.mem.njpb.entity.SysUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by six on 2017/7/21.
 */
public interface SysUserRepository extends CrudRepository<SysUser, Integer> {

    //根据用户id查询用户
    SysUser findOneById(@Param("id") Integer id);

    //根据用户账号查询
    SysUser findOneByUserAccount(@Param("userAccount") String userAccount);

    //用户名查重
    SysUser findOneByUserName(@Param("userName") String userName);

    List<SysUser> findByRemoveTimeIsNull();

    //根据用户ids查询用户
    List<SysUser> findByIdInOrderByLastUpdateTimeDesc(@Param("ids") List<Integer> ids);

}
