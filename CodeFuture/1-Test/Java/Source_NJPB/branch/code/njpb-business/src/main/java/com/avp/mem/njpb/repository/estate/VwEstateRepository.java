/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.estate;

import com.avp.mem.njpb.entity.view.VwEstate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Amber on 2017/9/5.
 */
public interface VwEstateRepository extends CrudRepository<VwEstate, Integer>, JpaSpecificationExecutor<VwEstate> {

    VwEstate findOneByEstateSn(@Param("estateSn") String estateSn);

    List<VwEstate> findByEstateTypeIdIn(@Param("estateTypeIds") List<Integer> estateTypeIds);

    //根据设备类型和项目id查询所有设备
    List<VwEstate> findByEstateTypeId(@Param("estateTypeId") Integer estateTypeId);

    VwEstate findTopByEstateTypeIdOrderByEstateNoDesc(@Param("estateTypeId") Integer estateTypeId);

    Page<VwEstate> findByCategory(@Param("category") Integer category, Pageable page);

    List<VwEstate> findByCategory(@Param("category") Integer category);

    VwEstate findTopByCategoryOrderByBikeFrameNoDesc(@Param("category") Integer category);

    VwEstate findByBikeFrameNo(@Param("bikeFrameNo") Integer bikeFrameNo);

    List<VwEstate> findByEstateTypeIdAndStationId(@Param("estateTypeId") Integer estateTypeId, @Param("stationId") Integer stationId);

    List<VwEstate> findOneByEstateNameAndStationId(@Param("estateName") String estateName, @Param("stationId") Integer stationId);


    VwEstate findOneById(@Param("id") Integer id);

    VwEstate findByBicycleStakeBarCode(@Param("bicycleStakeBarCode") String bicycleStakeBarCode);

}
