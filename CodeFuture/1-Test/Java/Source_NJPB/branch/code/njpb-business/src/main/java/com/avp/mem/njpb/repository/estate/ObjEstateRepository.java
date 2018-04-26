/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.estate;

import com.avp.mem.njpb.entity.estate.ObjEstate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by six on 2017/7/26.
 */
public interface ObjEstateRepository extends JpaRepository<ObjEstate, Integer> {

    // 根据ids查询
    List<ObjEstate> findByIdIn(@Param("ids") List<Integer> ids);

    List<ObjEstate> findByEstateSnIn(@Param("estateSns") List<String> estateSns);

//   List<ObjEstate> findByStationIdAndEstateTypeId(@Param("stationId") Integer stationId, @Param("estateTypeId") Integer estateTypeId);

    ObjEstate findByEstateSn(@Param("estateSn") String estateSn);

//    List<ObjEstate> findByEstateTypeIdIn(@Param("estateTypeIds") String estateTypeIds);

    ObjEstate findByEstateSnAndCategory(@Param("estateSn") String estateSn, @Param("category") Integer category);

    ObjEstate findByProjectIdAndStationIdAndEstateName(@Param("projectId") Integer projectId, @Param("stationId") Integer stationId, @Param("estateName") String estateName);

    ObjEstate findTopByEstateTypeIdAndProjectIdAndStationIdOrderByEstateNoDesc(@Param("estateTypeId") Integer estateTypeId, @Param("projectId") Integer projectId, @Param("stationId") Integer stationId);

    ObjEstate findTopByEstateTypeIdAndProjectIdOrderByEstateNoDesc(@Param("estateTypeId") Integer estateTypeId, @Param("projectId") Integer projectId);


    ObjEstate findTopByEstateTypeIdOrderByEstateNoDesc(@Param("estateTypeId") Integer estateTypeId);

    ObjEstate findTopByProjectIdOrderByEstateNoDesc(@Param("parentId") Integer parentId);


    List<ObjEstate> findByProjectId(@Param("projectId") Integer projectId);

    List<ObjEstate> findByEstateTypeIdAndProjectIdAndStationId(@Param("estateTypeId") Integer estateTypeId, @Param("projectId") Integer projectId, @Param("stationId") Integer stationId);

    List<ObjEstate> findByEstateTypeIdIn(@Param("estateTypeIds") List<Integer> estateTypeIds);

    // 根据模块类型id查询数量
    int countByEstateTypeId(@Param("estateSubTypeId") Integer estateSubTypeId);

    int countByBikeFrameNoBetween(@Param("bikeFrameNo1") Integer bikeFrameNo1, @Param("bikeFrameNo2") Integer bikeFrameNo2);

    ObjEstate findByBikeFrameNo(@Param("bikeFrameNo") Integer bikeFrameNo);

    ObjEstate findTopByOrderByBikeFrameNoDesc();

    ObjEstate findByBikeFrameNoAndIdNot(@Param("bikeFrameNo") Integer bikeFrameNo, @Param("id") Integer id);
}
