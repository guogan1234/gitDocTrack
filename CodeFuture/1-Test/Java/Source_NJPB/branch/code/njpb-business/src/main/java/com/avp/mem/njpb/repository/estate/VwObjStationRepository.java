/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.estate;

import com.avp.mem.njpb.entity.estate.VwObjStation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by six on 2017/8/10.
 */
public interface VwObjStationRepository extends CrudRepository<VwObjStation, Integer>, JpaSpecificationExecutor<VwObjStation> {

    VwObjStation  findByStationNo(@Param("stationNo") String stationNo);

    VwObjStation findByStationSn(@Param("stationSn") String stationSn);

    VwObjStation  findByStationNoAndProjectId(@Param("stationNo") String stationNo, @Param("projectId") Integer projectId);

    VwObjStation  findByStationNoName(@Param("stationNoName") String stationNoName);

    VwObjStation  findByStationNoNameAndProjectId(@Param("stationNoName") String stationNoName, @Param("projectId") Integer projectId);

    VwObjStation  findByStationName(@Param("stationName") String stationName);

    VwObjStation  findByStationNameAndProjectId(@Param("stationName") String stationName, @Param("projectId") Integer projectId);

    Page<VwObjStation> findByStationNoNameContaining(@Param("stationNoName") String stationNoName, Pageable page);

    Page<VwObjStation> findByProjectIdAndStationNoNameContaining(@Param("projectId") Integer projectId, @Param("stationNoName") String stationNoName, Pageable page);
}
