/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.resource.basic;

import com.avp.mem.njpb.api.rest.ResponseBuilder;
import com.avp.mem.njpb.api.rest.ResponseCode;
import com.avp.mem.njpb.api.rest.RestBody;
import com.avp.mem.njpb.entity.system.SysParam;
import com.avp.mem.njpb.repository.basic.SysParamRepository;
import com.avp.mem.njpb.util.MagicNumber;
import com.avp.mem.njpb.util.Validator;
import org.apache.poi.ss.usermodel.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

/**
 * Created by six on 2017/7/28.
 */
@RestController
public class SysParamController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SysParamController.class);

    @Autowired
    SysParamRepository sysParamRepository;

    /**
     * 参数查询
     *
     * @return
     */
    @RequestMapping(value = "sysParams", method = RequestMethod.GET)
    ResponseEntity<RestBody<SysParam>> findAll() {

        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            List<SysParam> sysParams = sysParamRepository.findAll();
            LOGGER.debug("查询系统参数成功,数据量为：{}", sysParams.size());
            builder.setResultEntity(sysParams, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * 参数修改
     *
     * @param sysParam
     * @return
     */
    @RequestMapping(value = "sysParams", method = RequestMethod.POST)
    ResponseEntity updateSysParam(@RequestBody SysParam sysParam) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            if (Validator.isNull(sysParam.getId())) {
                LOGGER.debug("前端传入的参数id为空");
                List<SysParam> sysParams = sysParamRepository.findAll();
                if (sysParams.size() != 0) {
                    LOGGER.debug("前端参数有误，数据库中参数数据已经存在");
                    builder.setResponseCode(ResponseCode.BAD_REQUEST);
                    return builder.getResponseEntity();
                }
                sysParam.setStockCheckVersion(MagicNumber.ONE);
            } else {
                Integer paramId = sysParam.getId();
                LOGGER.debug("数据库的参数id为：{}", paramId);
                SysParam sysParamDb = sysParamRepository.findOne(paramId);
                Date stockCheckStartTimeDb = sysParamDb.getStockCheckStartTime();
                Date stockCheckEndTimeDb = sysParamDb.getStockCheckEndTime();
                Date stockCheckStartTimeWeb = sysParamDb.getStockCheckStartTime();
                Date stockCheckEndTimeWeb = sysParamDb.getStockCheckEndTime();

                Integer stockCheckVersion = sysParamDb.getStockCheckVersion();

                if (Validator.equals(stockCheckStartTimeWeb, stockCheckStartTimeDb) && Validator.equals(stockCheckEndTimeWeb, stockCheckEndTimeDb)) {
                    LOGGER.debug("前端没有修改库存盘点时间,所以不对参数版本:{}进行累加", stockCheckVersion);
                } else {
                    LOGGER.debug("前端修改了库存盘点时间,所以需要对参数版本:{}进行累加", stockCheckVersion);
                    sysParam.setStockCheckVersion(stockCheckVersion == null ? 1 : stockCheckVersion + 1);
                }
            }
            sysParam = sysParamRepository.save(sysParam);

            LOGGER.debug("参数保存成功，盘点版本为：{}", sysParam.getStockCheckVersion());
            builder.setResultEntity(sysParam, ResponseCode.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.UPDATE_FAILED, "更新失败");
        }
        return builder.getResponseEntity();
    }
}
