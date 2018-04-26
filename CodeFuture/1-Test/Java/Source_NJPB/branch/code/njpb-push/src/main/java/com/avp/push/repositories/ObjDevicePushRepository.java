package com.avp.push.repositories;

import com.avp.push.entity.ObjDevicePush;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by six on 2017/8/24.
 */
public interface ObjDevicePushRepository extends CrudRepository<ObjDevicePush, Integer> {

    List<ObjDevicePush> findByUserIdIn(@Param("userIds") List<Integer> userIds);

    ObjDevicePush findOneByDeviceId(@Param("deviceId") String deviceId);
}
