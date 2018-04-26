/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.workorder;

import com.avp.mem.njpb.entity.view.VwScoreMonth;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by len on 2017/10/10.
 */
public interface VwScoreMonthRepository extends CrudRepository<VwScoreMonth, Integer> {

    Page<VwScoreMonth> findByUid(@Param("uid") Integer uid, Pageable page);

}
