/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.resource.stock;

import com.avp.mem.njpb.api.rest.ResponseBuilder;
import com.avp.mem.njpb.api.rest.ResponseCode;
import com.avp.mem.njpb.api.rest.RestBody;
import com.avp.mem.njpb.api.util.SecurityUtils;
import com.avp.mem.njpb.entity.estate.ObjEstate;
import com.avp.mem.njpb.entity.stock.ObjInventoryCheckRecord;
import com.avp.mem.njpb.entity.stock.ObjInventoryCheckRecordDetail;
import com.avp.mem.njpb.entity.system.SysParam;
import com.avp.mem.njpb.entity.system.SysUser;
import com.avp.mem.njpb.entity.view.VwInventoryCheckRecordDetail;
import com.avp.mem.njpb.repository.basic.SysParamRepository;
import com.avp.mem.njpb.repository.estate.ObjEstateRepository;
import com.avp.mem.njpb.repository.stock.ObjInventoryCheckRecordDetailRepository;
import com.avp.mem.njpb.repository.stock.ObjInventoryCheckRecordRepository;
import com.avp.mem.njpb.repository.stock.VwInventoryCheckRecordDetailRepository;
import com.avp.mem.njpb.repository.sys.SysUserRepository;
import com.avp.mem.njpb.util.BusinessRefData;
import com.avp.mem.njpb.service.stock.CheckTimeService;
import com.avp.mem.njpb.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by six on 2017/8/8.
 */
@RestController
public class CheckStockController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SysUserRepository sysUserRepository;

    @Autowired
    ObjEstateRepository objEstateRepository;

    @Autowired
    ObjInventoryCheckRecordRepository objInventoryCheckRecordRepository;

    @Autowired
    ObjInventoryCheckRecordDetailRepository objInventoryCheckRecordDetailRepository;

    @Autowired
    VwInventoryCheckRecordDetailRepository vwInventoryCheckRecordDetailRepository;

    @Autowired
    SysParamRepository sysParamRepository;

    @Autowired
    CheckTimeService checkTimeService;

    /**
     * 校验盘点时间
     *
     * @return
     */
    @RequestMapping(value = "inventoryChecks/checkTime", method = RequestMethod.GET)
    ResponseEntity checkTime() {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {

            SysParam sysParam = sysParamRepository.findOne(BusinessRefData.SYSTEM_PARAM_ID);
            if (Validator.isNull(sysParam)) {
                logger.debug("系统参数缺失，请查看数据库");
                builder.setResponseCode(ResponseCode.RETRIEVE_FAILED, "数据库缺失盘点时间参数,请预先设置");
                return builder.getResponseEntity();
            }

            Date dateBegin = sysParam.getStockCheckStartTime();
            Date dateEnd = sysParam.getStockCheckEndTime();
            if (Validator.isNull(dateBegin) || Validator.isNull(dateEnd)) {
                logger.debug("系统盘点时间参数有误，请检查设置");
                builder.setResponseCode(ResponseCode.RETRIEVE_FAILED, "系统盘点时间参数有误，请检查设置");
                return builder.getResponseEntity();
            }

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String dateBeginString = formatter.format(dateBegin);
            String dateEndString = formatter.format(dateEnd);

            if (checkTimeService.checkTimeBetweenStartAndEnd()) {
                logger.debug("时间校验合格");
                builder.setResponseCode(ResponseCode.OK, "可以盘点");
            } else {
                logger.debug("当前时间不在盘点时间");
                builder.setResponseCode(ResponseCode.FAILED, "盘点时间为[" + dateBeginString + "-" + dateEndString + "]，当前不可以盘点自行车");
            }
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * 盘点
     *
     * @return
     */
    @RequestMapping(value = "inventoryChecks", method = RequestMethod.POST)
    ResponseEntity saveInventoryCheckRecord(@RequestBody ObjInventoryCheckRecord objInventoryCheckRecord, @RequestParam List<String> estateSns) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            Integer uid = SecurityUtils.getLoginUserId();

            if (!checkTimeService.checkTimeBetweenStartAndEnd()) {
                logger.debug("当前时间不在盘点时间");
                builder.setResponseCode(ResponseCode.FAILED, "当前时间不在盘点时间");
                return builder.getResponseEntity();
            }

            SysParam sysParam = sysParamRepository.findOne(BusinessRefData.SYSTEM_PARAM_ID);
            if (Validator.isNull(sysParam)) {
                logger.debug("系统参数不存在,请查看数据库");
                builder.setResponseCode(ResponseCode.FAILED, "系统参数不存在,请查看数据库");
                return builder.getResponseEntity();
            }

            Integer stockParamVersion = sysParam.getStockCheckVersion();

            logger.debug("当前登录账户：{},数据库参数版本为：{}", uid, stockParamVersion);

            Integer corpId = objInventoryCheckRecord.getCorpId();
            Integer count = estateSns.size();

            logger.debug("公司：{}，盘点数据量:{},自行车estateEns数量：{}", corpId, count, estateSns.size());

            ObjInventoryCheckRecord inventoryCheckRecord = new ObjInventoryCheckRecord();

            logger.debug("获取公司：{}上一次盘点记录数据", corpId);
            ObjInventoryCheckRecord lastInventoryCheckRecord = objInventoryCheckRecordRepository.findTopByCorpIdOrderByParamVersionDesc(corpId);
//            if (Validator.isNull(lastInventoryCheckRecord)) {
//                logger.debug("该公司：{}没有上一次盘点记录", corpId);
//            } else {
//                Integer paramVersion = lastInventoryCheckRecord.getParamVersion();
//                Integer lastCount = lastInventoryCheckRecord.getCount();
//
//                logger.debug("该公司：{}上一次盘点记录,版本为：{},数量为：{}", corpId, paramVersion, count);
//                if (Validator.equals(paramVersion, stockParamVersion)) {
//                    logger.debug("数据库里面已经存在该公司的当前版本的盘点数据，需要对数据进行累加");
//                    count = count + lastCount;
//                }
//            }

            logger.debug("公司：{}，数量：{}，盘点人：{}", corpId, count, uid);

            inventoryCheckRecord.setCount(count);
            inventoryCheckRecord.setCorpId(corpId);
            inventoryCheckRecord.setCheckUserId(uid);
            inventoryCheckRecord.setCheckTime(new Date());
            inventoryCheckRecord.setParamVersion(sysParam.getStockCheckVersion());
            inventoryCheckRecord.setCheckRemark(objInventoryCheckRecord.getCheckRemark());
            inventoryCheckRecord.setStationId(objInventoryCheckRecord.getStationId());

            objInventoryCheckRecord = objInventoryCheckRecordRepository.save(inventoryCheckRecord);
            logger.debug("盘点记录添加成功，id为：{},数量：{}", objInventoryCheckRecord.getId(), count);

            //关联盘点详情
            List<ObjInventoryCheckRecordDetail> objInventoryCheckRecordDetails = new ArrayList<>();
            ObjEstate objEstate = new ObjEstate();
            for (String temp : estateSns) {
                //盘点详情
                ObjInventoryCheckRecordDetail objInventoryCheckRecordDetail = new ObjInventoryCheckRecordDetail();
                objInventoryCheckRecordDetail.setEstateSn(temp);
                objInventoryCheckRecordDetail.setInventoryCheckRecordId(objInventoryCheckRecord.getId());
                objInventoryCheckRecordDetails.add(objInventoryCheckRecordDetail);
            }

            List<ObjInventoryCheckRecordDetail> objInventoryCheckRecordDetail = objInventoryCheckRecordDetailRepository.save(objInventoryCheckRecordDetails);
            logger.debug("盘点详情添加成功，数量为：{}", objInventoryCheckRecordDetail.size());
            builder.setResponseCode(ResponseCode.OK, "此次盘点成功，继续加油");
        } catch (Exception e) {
            logger.error(e.getMessage());
            builder.setResponseCode(ResponseCode.CREATE_FAILED, "此次盘点失败，请重新盘点");
        }
        return builder.getResponseEntity();
    }

    /**
     * (上拉加载更多)-查询盘点记录-APP
     *
     * @param firstOperationTime
     * @param page
     * @return
     */
    @RequestMapping(value = "inventoryChecks/findByOperationTimeLessThan", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwInventoryCheckRecordDetail>> findByOperationTimeLessThan(Date firstOperationTime, Pageable page) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        //firstOperationTime = new Date();
        logger.debug("inventoryChecks/findByOperationTimeLessThan, firstOperationTime为({}),p为({})",
                firstOperationTime, page);
        try {
            SysParam sysParam = sysParamRepository.findOne(BusinessRefData.SYSTEM_PARAM_ID);
            if (Validator.isNull(sysParam)) {
                logger.debug("系统参数不存在,请查看数据库");
                builder.setResponseCode(ResponseCode.FAILED, "系统参数不存在,请查看数据库");
                return builder.getResponseEntity();
            }
            Integer uid = SecurityUtils.getLoginUserId();

            if (firstOperationTime == null) {
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                logger.debug("参数缺失，请求失败");
                return builder.getResponseEntity();
            }
            logger.debug("盘点记录上拉加载更多的操作,首次操作时间 : " + firstOperationTime);
            Page<VwInventoryCheckRecordDetail> list = vwInventoryCheckRecordDetailRepository.findByCheckUserIdAndParamVersionAndLastUpdateTimeLessThan(uid, sysParam.getStockCheckVersion(), firstOperationTime, page);
            if (list.getTotalElements() <= 0) {
                logger.debug("查询盘点记录成功，结果为空");
            } else {
                logger.debug("查询盘点记录成功，数据量为:({})", list.getTotalElements());
            }
            builder.setResultEntity(list, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * (下拉刷新)-查询盘点记录-APP
     *
     * @param firstOperationTime
     * @return
     */
    @RequestMapping(value = "inventoryChecks/findByOperationTimeGreaterThan", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwInventoryCheckRecordDetail>> findByOperationTimeGreaterThan(Date firstOperationTime) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        logger.debug("inventoryChecks/findByOperationTimeGreaterThan, firstOperationTime为({})",
                firstOperationTime);
        try {
            Integer uid = SecurityUtils.getLoginUserId();
            if (firstOperationTime == null) {
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                logger.debug("参数缺失，请求失败");
                return builder.getResponseEntity();
            }
            SysParam sysParam = sysParamRepository.findOne(BusinessRefData.SYSTEM_PARAM_ID);
            if (Validator.isNull(sysParam)) {
                logger.debug("系统参数不存在,请查看数据库");
                builder.setResponseCode(ResponseCode.FAILED, "系统参数不存在,请查看数据库");
                return builder.getResponseEntity();
            }
            logger.debug("盘点记录下拉加载更多的操作,首次操作时间 : " + firstOperationTime);
            List<VwInventoryCheckRecordDetail> list = vwInventoryCheckRecordDetailRepository.findByCheckUserIdAndParamVersionAndLastUpdateTimeGreaterThan(uid, sysParam.getStockCheckVersion(), firstOperationTime);
            if (list.size() <= 0) {
                logger.debug("查询盘点记录成功，结果为空");
            } else {
                logger.debug("查询盘点记录成功，数据量为:({})", list.size());
            }
            builder.setResultEntity(list, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }


    /**
     * 我的盘点-获取上次盘点数量和本次盘点总量
     *
     * @return
     */
    @RequestMapping(value = "inventoryChecks/getCheckSumCount", method = RequestMethod.GET)
    ResponseEntity getCheckSumCount() {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            Map<String, Integer> sumCount = new HashMap<>();
            Integer uid = SecurityUtils.getLoginUserId();
            SysUser sysUser = sysUserRepository.findOne(uid);

            SysParam sysParam = sysParamRepository.findOne(BusinessRefData.SYSTEM_PARAM_ID);
            if (Validator.isNull(sysParam)) {
                logger.debug("系统参数不存在,请查看数据库");
                builder.setResponseCode(ResponseCode.FAILED, "系统参数不存在,请查看数据库");
                return builder.getResponseEntity();
            }

            Integer sysParamVersion = sysParam.getStockCheckVersion();
            Integer corpId = sysUser.getCorpId();
            //个人版本盘点
            Integer personCheckNow = objInventoryCheckRecordRepository.findByCheckUserIdAndParamVersion(uid, sysParamVersion);
            if (Validator.isNull(personCheckNow)) {
                logger.debug("个人本次盘点车辆总数结果为空");
                sumCount.put("personCheckNow", 0);
            } else {
                logger.debug("个人本次盘点车辆总数：{}", personCheckNow);
                sumCount.put("personCheckNow", personCheckNow);
            }

            //公司版本盘点
            Integer corpCheckNow = objInventoryCheckRecordRepository.findByCorpIdAndParamVersion(corpId, sysParamVersion);
            if (Validator.isNull(corpCheckNow)) {
                logger.debug("公司本次盘点车辆总数结果为空");
                sumCount.put("corpCheckNow", 0);
            } else {
                logger.debug("公司本次盘点车辆总数：{}", corpCheckNow);
                sumCount.put("corpCheckNow", corpCheckNow);
            }

            ObjInventoryCheckRecord objInventoryCheckRecord = objInventoryCheckRecordRepository.findTopByCorpIdAndParamVersionLessThanOrderByParamVersionDesc(corpId, sysParamVersion);
            if (Validator.isNull(objInventoryCheckRecord)) {
                logger.debug("查询上次版本记录数据结果为空");
                builder.setResponseCode(ResponseCode.FAILED, "查询上次版本记录数据结果为空");
                sumCount.put("personCheckLast", 0);
                sumCount.put("corpCheckLast", 0);
            } else {
                Integer personCheckLast = objInventoryCheckRecordRepository.findByCheckUserIdAndParamVersion(uid, objInventoryCheckRecord.getParamVersion());
                //个人上一次版本盘点

                if (Validator.isNull(personCheckLast)) {
                    logger.debug("个人上次盘点车辆总数结果为空");
                    sumCount.put("personCheckLast", 0);
                } else {
                    logger.debug("公司本次盘点车辆总数：{}", personCheckLast);
                    sumCount.put("personCheckLast", personCheckLast);
                }

                Integer corpCheckLast = objInventoryCheckRecordRepository.findByCorpIdAndParamVersion(corpId, objInventoryCheckRecord.getParamVersion());
                //公司上一次版本盘点

                if (Validator.isNull(corpCheckLast)) {
                    logger.debug("公司上次盘点车辆总数结果为空");
                    sumCount.put("corpCheckLast", 0);
                } else {
                    logger.debug("公司上次盘点车辆总数：{}", corpCheckLast);
                    sumCount.put("corpCheckLast", corpCheckLast);
                }

            }
            builder.setResultEntity(sumCount, ResponseCode.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

}
