/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.resource.basic;

import com.avp.mem.njpb.api.rest.ResponseBuilder;
import com.avp.mem.njpb.api.rest.ResponseCode;
import com.avp.mem.njpb.api.rest.RestBody;
import com.avp.mem.njpb.api.util.SecurityUtils;
import com.avp.mem.njpb.entity.basic.ObjCorporation;
import com.avp.mem.njpb.repository.basic.ObjCorporationRepository;
import com.avp.mem.njpb.util.BusinessRefData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by six on 2017/7/28.
 */
@RestController
public class CorporationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CorporationController.class);

    @Autowired
    ObjCorporationRepository objCorporationRepository;

    /**
     * 非总公司的公司数据查询公司查询
     *
     * @return
     */
    @RequestMapping(value = "corporations/findByCorpLevel", method = RequestMethod.GET)
    ResponseEntity<RestBody<ObjCorporation>> findByCorpLevel() {
        ResponseBuilder<ObjCorporation> builder = ResponseBuilder.createBuilder();
        try {
            List<ObjCorporation> corporations = objCorporationRepository.findByCorpLevelNot(BusinessRefData.CORP_LEVEL_ZERO);

            LOGGER.debug("查询公司成功,数据量为：{}", corporations.size());
            builder.setResultEntity(corporations, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * 公司数据查询
     *
     * @return
     */
    @RequestMapping(value = "corporations", method = RequestMethod.GET)
    ResponseEntity<RestBody<ObjCorporation>> findAll() {
        ResponseBuilder<ObjCorporation> builder = ResponseBuilder.createBuilder();
        try {
            List<ObjCorporation> corporations = objCorporationRepository.findByOrderByCorpNo();

            LOGGER.debug("查询公司成功,数据量为：{}", corporations.size());
            builder.setResultEntity(corporations, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * 插叙当前账号的公司
     *
     * @return
     */
    @RequestMapping(value = "corporations/findByUid", method = RequestMethod.GET)
    ResponseEntity<RestBody<ObjCorporation>> findByUid() {
        ResponseBuilder<ObjCorporation> builder = ResponseBuilder.createBuilder();
        try {
            Integer uid = SecurityUtils.getLoginUserId();

            List<ObjCorporation> corporations = objCorporationRepository.findByUid(uid);

            LOGGER.debug("查询公司成功,数据量为：{}", corporations.size());
            builder.setResultEntity(corporations, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }
}
