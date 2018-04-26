/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.resource.basic;


import com.avp.mem.njpb.api.rest.ResponseBuilder;
import com.avp.mem.njpb.api.rest.ResponseCode;
import com.avp.mem.njpb.api.rest.RestBody;
import com.avp.mem.njpb.entity.system.SysVersionApp;
import com.avp.mem.njpb.repository.sys.SysVersionAppRepository;
import com.avp.mem.njpb.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Boris.F on 2017/1/10.
 */
@RestController
public class SystemController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SystemController.class);

    @Autowired
    SysVersionAppRepository sysVersionAppRepository;



    // 手机app版本
    @RequestMapping(value = "versions/findTopAppVersionByOsType", method = RequestMethod.GET)
    ResponseEntity<RestBody<SysVersionApp>>  findTopAppVersionByOsType() {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
           SysVersionApp sysVersionApp =  sysVersionAppRepository.findTopByOrderByCreateTimeDesc();

            LOGGER.debug("SysVersionApp的版本号: {}", sysVersionApp.getVersionNo());
            builder.setResultEntity(sysVersionApp, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            LOGGER.error("findTopAppVersionByOsType {}", e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }

        return builder.getResponseEntity();
    }

    /**
     * 版本-新建
     *
     * @param sysVersionApp
     * @return
     */
    @RequestMapping(value = "sysVersions", method = RequestMethod.POST)
    ResponseEntity saveObjEstateSupplier(@RequestBody SysVersionApp sysVersionApp) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            if (Validator.isNotNull(sysVersionApp)) {
                SysVersionApp sysVersionApp1 = sysVersionAppRepository.save(sysVersionApp);
                builder.setResultEntity(sysVersionApp1, ResponseCode.CREATE_SUCCEED);
            } else {
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.CREATE_FAILED);
        }
        return builder.getResponseEntity();
    }

}
