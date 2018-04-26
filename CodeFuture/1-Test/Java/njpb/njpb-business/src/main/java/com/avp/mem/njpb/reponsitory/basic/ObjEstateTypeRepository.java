package com.avp.mem.njpb.reponsitory.basic;

import com.avp.mem.njpb.entity.ObjEstateType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Amber Wang on 2017-07-17 下午 06:36.
 */
public interface ObjEstateTypeRepository extends CrudRepository<ObjEstateType,Integer> {

    List<ObjEstateType> findByRemoveTimeIsNull();

    //根据设备名查询设备
    ObjEstateType findByNameAndRemoveTimeIsNull(@Param("name") String name);

    //根据设备ids查询设备类型
    List<ObjEstateType> findByIdInAndRemoveTimeIsNull(@Param("ids") List<Integer> ids);
}
