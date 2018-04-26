/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avp.mems.push.repositories;

import com.avp.mems.push.entities.PushInfo;
import com.avp.mems.push.entities.PushInfoPK;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author GD
 */
public interface PushInfoRepository extends CrudRepository<PushInfo, PushInfoPK> {
    //List findByUsername(@Param("username") String username);
//    List findByPushInfoPkUsernameIn(@Param("usernames") Collection<String> usernames);

    @Query("select p from PushInfo p where p.pushInfoPK.username in :usernames")
    List findByUserNames(@Param("usernames") Collection<String> usernames);

    @Query("select p from PushInfo p where p.pushInfoPK.username = :username and p.pushInfoPK.os = :os")
    List findByUserNamesAndOs(@Param("username") String username , @Param("os") String os);
}
