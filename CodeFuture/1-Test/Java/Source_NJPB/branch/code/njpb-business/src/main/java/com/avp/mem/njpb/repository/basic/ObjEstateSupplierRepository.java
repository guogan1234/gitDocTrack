/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.basic;

import com.avp.mem.njpb.entity.estate.ObjEstateSupplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by six on 2017/7/19.
 */
public interface ObjEstateSupplierRepository extends JpaRepository<ObjEstateSupplier, Integer> {


    //根据供应商名称查询
    ObjEstateSupplier findOneBySupplierName(@Param("supplierName") String supplierName);

    //根据供应商ids查询供应商
    List<ObjEstateSupplier> findByIdIn(@Param("ids") List<Integer> ids);


}
