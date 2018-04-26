package com.avp.mems.backstage.repositories.user;

import com.avp.mems.backstage.entity.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by zhoujs on 2017/5/27.
 */
public interface UserRepository extends PagingAndSortingRepository<User, String> {
    User findByUsername(@Param("username") String username);

    User findByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    @Query(value = "select au.* from app_user au where au.username in (select ur.user_name from user_role ur where ur.role_name = :roleName)", nativeQuery = true)
    List<User> findByRoleName(@Param("roleName") String roleName);

    User findByWechatid(@Param("wechatid") String wechatid);

    List<User> findByUsernameIn(@Param("usernames") List<String> usernames);

}
