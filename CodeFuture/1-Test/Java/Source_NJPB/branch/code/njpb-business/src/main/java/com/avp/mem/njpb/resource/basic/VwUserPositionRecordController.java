/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.resource.basic;

import com.avp.mem.njpb.api.rest.ResponseBuilder;
import com.avp.mem.njpb.api.rest.ResponseCode;
import com.avp.mem.njpb.api.rest.RestBody;
import com.avp.mem.njpb.entity.view.VwUserPositionRecord;
import com.avp.mem.njpb.repository.sys.VwUserPositionRecordRepository;
import com.avp.mem.njpb.repository.workorder.VwWorkOrderRepository;
import com.avp.mem.njpb.util.DateUtil;
import com.avp.mem.njpb.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Amber Wang on 2017-09-19 下午 04:27.
 */
@RestController
public class VwUserPositionRecordController {

    private static final Logger LOGGER = LoggerFactory.getLogger(VwUserPositionRecordController.class);

    @Autowired
    VwUserPositionRecordRepository vwUserPositionRecordRepository;

    @Autowired
    VwWorkOrderRepository vwWorkOrderRepository;

    /**
     * PC地图-查询所有人上次在线位置
     *
     * @return
     */
    @RequestMapping(value = "userPositionRecords/findLastPositionByCorpId", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwUserPositionRecord>> findLastPosition(Integer corpId, @RequestParam(value = "startTime", required = false) Date startTime,
                                                                    @RequestParam(value = "endTime", required = false) Date endTime
    ) {

        ResponseBuilder builder = ResponseBuilder.createBuilder();
        List<VwUserPositionRecord> vwUserPositionRecords = new ArrayList<>();
        try {
            Date startDate = new Date();
            Date endDate = new Date();
            if (Validator.isNull(startTime)) {
                startDate = DateUtil.getThisWeekBeginTime(new Date());

                endDate = DateUtil.getThisWeekEndTime(new Date());
            } else {
                startDate = startTime;
                endDate = endTime;
            }


//            Date endDate = DateUtil.getThisWeekEndTime(new Date());

            LOGGER.debug("本周开始时间为：{}，结束时间为：{}", startDate.toLocaleString(), endDate.toLocaleString());

            if (Validator.isNull(corpId)) {
                vwUserPositionRecords = vwUserPositionRecordRepository.findLastPosition(startDate, endDate);
            } else {
                vwUserPositionRecords = vwUserPositionRecordRepository.findLastPositionByCorpId(startDate, endDate, corpId);
            }

            LOGGER.debug("查询人员位置信息成功，数据量为：{}", vwUserPositionRecords.size());

            builder.setResultEntity(vwUserPositionRecords, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * PC地图-查询指定人员轨迹数据
     *
     * @return
     */
    @RequestMapping(value = "userPositionRecords/findByUserId", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwUserPositionRecord>> findByUserId(Integer userId, @RequestParam(value = "startTime", required = false) Date startTime,
                                                                @RequestParam(value = "endTime", required = false) Date endTime) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        List<VwUserPositionRecord> vwUserPositionRecords = new ArrayList<>();
        try {

//            Date startDate = DateUtil.getThisWeekBeginTime(new Date());
//
//            Date endDate = DateUtil.getThisWeekEndTime(new Date());

            Date startDate = new Date();
            Date endDate = new Date();
            if (Validator.isNull(startTime)) {
                startDate = DateUtil.getThisWeekBeginTime(new Date());

                endDate = DateUtil.getThisWeekEndTime(new Date());
            } else {
                startDate = startTime;
                endDate = endTime;
            }

            LOGGER.debug("本周开始时间为：{}，结束时间为：{}", startDate.toLocaleString(), endDate.toLocaleString());

            vwUserPositionRecords = vwUserPositionRecordRepository.findByUserIdAndRecordTimeBetweenOrderByRecordTimeDesc(userId, startDate, endDate);

            LOGGER.debug("用户：{}的轨迹数据条目为：{}", userId, vwUserPositionRecords.size());

            builder.setResultEntity(vwUserPositionRecords, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * PC地图-查询个人上次在线位置
     *
     * @return
     */
    @RequestMapping(value = "userPositionRecords/findLastPositionByUserId", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwUserPositionRecord>> findLastPositionUser(Integer userId, @RequestParam(value = "startTime", required = false) Date startTime,
                                                                        @RequestParam(value = "endTime", required = false) Date endTime) {

        ResponseBuilder builder = ResponseBuilder.createBuilder();
        VwUserPositionRecord vwUserPositionRecord = new VwUserPositionRecord();
        try {

//            Date startDate = DateUtil.getThisWeekBeginTime(new Date());
//
//            Date endDate = DateUtil.getThisWeekEndTime(new Date());
            Date startDate = new Date();
            Date endDate = new Date();
            if (Validator.isNull(startTime)) {
                startDate = DateUtil.getThisWeekBeginTime(new Date());

                endDate = DateUtil.getThisWeekEndTime(new Date());
            } else {
                startDate = startTime;
                endDate = endTime;
            }
            LOGGER.debug("本周开始时间为：{}，结束时间为：{}", startDate.toLocaleString(), endDate.toLocaleString());

            vwUserPositionRecord = vwUserPositionRecordRepository.findLastPositionByUserId(startDate, endDate, userId);

            LOGGER.debug("查询人员位置信息成功：{}", vwUserPositionRecord.toString());

            builder.setResultEntity(vwUserPositionRecord, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }






}
