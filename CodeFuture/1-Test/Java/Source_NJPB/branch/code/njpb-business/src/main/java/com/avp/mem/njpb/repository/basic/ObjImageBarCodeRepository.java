/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.basic;

import com.avp.mem.njpb.entity.estate.ObjBarcodeImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by len on 2016/12/6.
 */
public interface ObjImageBarCodeRepository extends JpaRepository<ObjBarcodeImage, Integer> {

    ObjBarcodeImage findOneByBarCodeSn(@Param("barCodeSn") String barCodeSn);

    ObjBarcodeImage findOneByBarCodeSnAndRelation(@Param("barCodeSn") String barCodeSn, @Param("relation") Integer relation);

    // 根据ids查询
    List<ObjBarcodeImage> findByIdIn(@Param("ids") List<Integer> ids);

    ObjBarcodeImage findOneById(@Param("id") Integer id);

    Page<ObjBarcodeImage> findByRelation(@Param("relation") Integer relation, Pageable page);
}

