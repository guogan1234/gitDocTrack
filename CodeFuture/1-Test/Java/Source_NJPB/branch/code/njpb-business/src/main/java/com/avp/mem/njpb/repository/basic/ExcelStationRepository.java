/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.repository.basic;

import com.avp.mem.njpb.entity.ObjStationExcel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by len on 2017/10/24.
 */
public interface ExcelStationRepository extends JpaRepository<ObjStationExcel, Integer> {
}
