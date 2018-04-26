/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.estate;


import com.avp.mem.njpb.entity.view.VwUserEstateBarCode;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by len on 2017/1/10.
 */
public interface VwUserEstateBarCodeRepository extends CrudRepository<VwUserEstateBarCode, Integer>, JpaSpecificationExecutor<VwUserEstateBarCode> {
    //根据模块查询设备/模块二维码
    List<VwUserEstateBarCode> findByUid(@Param("uid") Integer uid);

    //根据选中的设备id查询相应的设备模块
    List<VwUserEstateBarCode> findByUidAndIdIn(@Param("uid") Integer uid, @Param("ids") List<Integer> ids);

    List<VwUserEstateBarCode> findByBarCodeSnAndUid(@Param("barCodeSn") String barCodeSn, @Param("uid") Integer uid);



}
