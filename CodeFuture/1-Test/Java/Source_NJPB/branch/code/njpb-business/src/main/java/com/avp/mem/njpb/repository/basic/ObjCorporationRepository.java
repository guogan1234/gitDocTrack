/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.basic;

import com.avp.mem.njpb.entity.basic.ObjCorporation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by six on 2017/7/28.
 */
public interface ObjCorporationRepository extends JpaRepository<ObjCorporation, Integer> {
    List<ObjCorporation> findByCorpLevelNot(@Param("corpLevel") Integer corpLevel);

    List<ObjCorporation> findByOrderByCorpNo();

    @Query(value = "SELECT oc.* FROM business.asso_user_project aup   JOIN business.obj_corporation oc ON oc.remove_time IS NULL AND aup.remove_time IS NULL AND oc.id = aup.project_id WHERE aup.user_id = :uid ORDER by oc.id", nativeQuery = true)
    List<ObjCorporation> findByUid(@Param("uid") Integer uid);
}
