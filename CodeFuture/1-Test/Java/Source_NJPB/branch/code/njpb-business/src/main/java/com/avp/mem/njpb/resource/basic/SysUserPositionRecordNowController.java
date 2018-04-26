/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.resource.basic;

import com.avp.mem.njpb.api.rest.ResponseBuilder;
import com.avp.mem.njpb.api.rest.ResponseCode;
import com.avp.mem.njpb.api.rest.RestBody;
import com.avp.mem.njpb.entity.basic.SysUserPositionRecordNow;
import com.avp.mem.njpb.entity.view.VwSysUserPositionRecordNow;
import com.avp.mem.njpb.repository.basic.SysUserPositionRecordNowRepository;
import com.avp.mem.njpb.repository.basic.VwSysUserPositionRecordNowRepository;
import com.avp.mem.njpb.repository.sys.SysUserRepository;
import com.avp.mem.njpb.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by len on 2018/2/22.
 */

@RestController
public class SysUserPositionRecordNowController {
//    private static final Logger LOGGER = LoggerFactory.getLogger(SysUserPositionRecordNowController.class);


    @Autowired
    SysUserPositionRecordNowRepository sysUserPositionRecordNowRepository;
    @Autowired
    SysUserRepository sysUserRepository;
    @Autowired
    VwSysUserPositionRecordNowRepository vwSysUserPositionRecordNowRepository;

    /**
     * 上传个人位置
     *
     * @param sysUserPositionRecordNow
     * @return
     */
    @RequestMapping(value = "userPositionRecordNow", method = RequestMethod.POST)
    ResponseEntity saveUserPositionRecordNow(@RequestBody SysUserPositionRecordNow sysUserPositionRecordNow) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            if (Validator.isNotNull(sysUserPositionRecordNow)) {
                SysUserPositionRecordNow sysUserPositionRecordNow1 = sysUserPositionRecordNowRepository.save(sysUserPositionRecordNow);
                builder.setResponseCode(ResponseCode.CREATE_SUCCEED);
            } else {
//                LOGGER.debug("传入对象为空");
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
            }
        } catch (Exception e) {
//            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.CREATE_FAILED);
        }
        return builder.getResponseEntity();
    }


    /**
     * @return
     */
    @RequestMapping(value = "userPositionRecordsNow/findLastPositionByCorpId", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwSysUserPositionRecordNow>> findLastPosition(Integer corpId, @RequestParam(value = "startDate", required = false) Date startDate,
                                                                    @RequestParam(value = "endDate", required = false) Date endDate, @RequestParam(value = "userId", required = false) Integer userId) {

        ResponseBuilder builder = ResponseBuilder.createBuilder();
        List<VwSysUserPositionRecordNow> vwSysUserPositionRecordNows = new ArrayList<>();
        try {
            if (Validator.isNotNull(corpId) && Validator.isNotNull(startDate) && Validator.isNotNull(endDate)) {

                if (Validator.isNotNull(userId)) {
                    vwSysUserPositionRecordNows = vwSysUserPositionRecordNowRepository.findByUserIdAndCreateTimeBetweenOrderByCreateTimeDesc(userId, startDate, endDate);
                } else {

                    if (corpId == 1) {
                        vwSysUserPositionRecordNows = vwSysUserPositionRecordNowRepository.findLastPositionByCreateTime(startDate, endDate);

                    } else {
                        vwSysUserPositionRecordNows = vwSysUserPositionRecordNowRepository.findLastPositionByCorpIdAndCreateTime(startDate, endDate, corpId);
                    }
                }
                builder.setResultEntity(vwSysUserPositionRecordNows, ResponseCode.OK);
            } else {
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
            }

        } catch (Exception e) {
//            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

}
