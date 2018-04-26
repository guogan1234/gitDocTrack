package com.avp.mems.backstage.repositories.user;

import com.avp.mems.backstage.entity.basic.Location;
import com.avp.mems.backstage.entity.user.User;
import com.avp.mems.backstage.entity.user.UserProfession;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Amber Wang on 2017-05-29 下午 05:30.
 */
public interface UserProfessionRepository extends CrudRepository<UserProfession, Integer> {

    @Query("select l.userName from UserProfession l where l.professionId =:professionId")
    List<String> findByProfessionId(@Param("professionId") Integer professionId);

    @Query("select l.professionId from UserProfession l where l.userName =:userName")
    List<Integer> findByUserName(@Param("userName") String userName);
}
