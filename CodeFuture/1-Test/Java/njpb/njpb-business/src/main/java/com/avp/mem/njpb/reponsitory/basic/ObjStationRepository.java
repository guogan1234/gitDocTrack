package com.avp.mem.njpb.reponsitory.basic;

import com.avp.mem.njpb.entity.ObjStation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by six on 2017/7/18.
 */
public interface ObjStationRepository extends CrudRepository<ObjStation,Integer> {

    List<ObjStation> findByRemoveTimeIsNull();

    //根据站点名称查询
    ObjStation findByStationNameAndRemoveTimeIsNull(@Param("stationName") String stationName);

    //根据站点ids查询
    List<ObjStation> findByIdInAndRemoveTimeIsNull(@Param("ids") List<Integer> ids);


}
