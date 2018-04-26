package com.avp.mems.push.repositories;

import com.avp.mems.push.entities.PushInfoWechat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by len on 2017/6/2.
 */
public interface PushInfoWechatRepository extends CrudRepository<PushInfoWechat,String> {

    List<PushInfoWechat> findByUsernameInAndWechatidIsNotNull(@Param("usernames")List<String> usernames);

}
