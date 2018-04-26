/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.estate;

import com.avp.mem.njpb.entity.view.VwUserEstate;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by six on 2017/7/26.
 */
public interface VwUserEstateRepository extends CrudRepository<VwUserEstate, Integer>, JpaSpecificationExecutor<VwUserEstate> {

    VwUserEstate findOneById(@Param("id") Integer id);

    List<VwUserEstate> findByIdInAndUid(@Param("ids") List<Integer> ids, @Param("uid") Integer uid);

    //VwUserEstate findByProjectIdAndStationIdAndEstateTypeIdAndEstateName(@Param("projectId") Integer projectId,@Param("stationId") Integer stationId,@Param("estateTypeId") Integer estateTypeId,@Param("estateName") String estateName);

    //根据设备类型和项目id查询所有设备
    List<VwUserEstate> findByEstateTypeIdAndUid(@Param("estateTypeId") Integer estateTypeId, @Param("uid") Integer uid);
}
