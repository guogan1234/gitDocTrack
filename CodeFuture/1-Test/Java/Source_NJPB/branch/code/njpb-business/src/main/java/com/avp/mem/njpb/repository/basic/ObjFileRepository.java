/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.basic;

import com.avp.mem.njpb.entity.basic.ObjFile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Amber Wang
 * @date 2017年8月9日11:37:46
 */
public interface ObjFileRepository extends CrudRepository<ObjFile, Integer> {

//    ObjEstate findByEstateSnAndIsDeleteIsNull(@Param("barCodeSn")String barCodeSn);
//
//    //根据parentId查询子模块列表
//    List<ObjEstate> findByParentIdAndIsDeleteIsNull(@Param("parentId")Integer parentId);

    //根据id查询
    ObjFile findOneById(@Param("id") Integer id);

    //根据文件id(MD5)查询
    ObjFile findOneByFileId(@Param("fileId") String fileId);

    //根据二维码查询设备
//    ObjEstate findOneByEstateSnAndIsDeleteIsNull(@Param("barCodeSn")String barCodeSn);

    List<ObjFile> findByIdIn(@Param("Ids") List<Integer> ids);

    List<ObjFile> findByFileIdIn(@Param("fileIds") List<String> fileIds);
}
