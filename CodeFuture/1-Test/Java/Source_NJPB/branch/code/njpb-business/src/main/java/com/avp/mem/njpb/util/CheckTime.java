/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.util;

import com.avp.mem.njpb.entity.system.SysParam;
import com.avp.mem.njpb.repository.basic.SysParamRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by six on 2017/8/23.
 */
@Service
public class CheckTime {

    @Autowired
    SysParamRepository sysParamRepository;

    private final Logger logger = Logger.getLogger(CheckTime.class);

    public boolean checkTimeBetweenStartAndEnd() {

        SysParam sysParam = sysParamRepository.findOne(BusinessRefData.SYSTEM_PARAM_ID);
        if (Validator.isNull(sysParam)) {
            logger.debug("系统参数缺失，请查看数据库");
            return false;
        }
        Date now = new Date();
        Date dateBegin = sysParam.getStockCheckStartTime();
        Date dateEnd = sysParam.getStockCheckEndTime();
        if (dateBegin == null || dateEnd == null) {
            return false;
        }

        return dateBegin.before(now) && dateEnd.after(now);
    }

}


