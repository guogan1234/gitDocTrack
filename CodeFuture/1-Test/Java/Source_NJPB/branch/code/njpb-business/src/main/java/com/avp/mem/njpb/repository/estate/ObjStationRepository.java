/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.estate;

import com.avp.mem.njpb.entity.estate.ObjStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by six on 2017/7/18.
 */
public interface ObjStationRepository extends JpaRepository<ObjStation, Integer>, JpaSpecificationExecutor<ObjStation> {


    //根据站点名称查询
    ObjStation findByStationName(@Param("stationName") String stationName);

    ObjStation findByStationNo(@Param("stationNo") String stationNo);

    ObjStation findByStationSn(@Param("stationSn") String stationSn);

    //根据站点名称查询
    ObjStation findByStationNameAndIdNot(@Param("stationName") String stationName, @Param("id") Integer id);

    ObjStation findByStationNoAndIdNot(@Param("stationNo") String stationNo, @Param("id") Integer id);

    //根据站点ids查询
    List<ObjStation> findByIdIn(@Param("ids") List<Integer> ids);


    List<ObjStation> findByProjectId(@Param("projectId") Integer projectId);


}
