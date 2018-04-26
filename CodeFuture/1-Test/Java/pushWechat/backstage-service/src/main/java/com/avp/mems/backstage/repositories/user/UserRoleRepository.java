package com.avp.mems.backstage.repositories.user;

import com.avp.mems.backstage.entity.user.UserRole;
import com.avp.mems.backstage.entity.user.UserRolePK;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Amber Wang on 2017-06-14 下午 06:37.
 */
public interface UserRoleRepository  extends PagingAndSortingRepository<UserRole, UserRolePK> {
}
