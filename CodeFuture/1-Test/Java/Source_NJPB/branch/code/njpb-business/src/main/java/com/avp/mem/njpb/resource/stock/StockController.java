/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.resource.stock;

import com.avp.mem.njpb.api.rest.ResponseBuilder;
import com.avp.mem.njpb.api.rest.ResponseCode;
import com.avp.mem.njpb.api.rest.RestBody;
import com.avp.mem.njpb.api.util.SecurityUtils;
import com.avp.mem.njpb.entity.stock.ObjStockRecord;
import com.avp.mem.njpb.entity.stock.VwStockRecordPersonal;
import com.avp.mem.njpb.entity.stock.VwStockWorkOrderDetail;
import com.avp.mem.njpb.entity.stock.VwStockWorkOrderDetail_;
import com.avp.mem.njpb.entity.view.VwStockRecord;
import com.avp.mem.njpb.entity.view.VwStockRecordPersonalHistory;
import com.avp.mem.njpb.entity.view.VwStockRecord_;
import com.avp.mem.njpb.repository.stock.ObjStockRecordRepository;
import com.avp.mem.njpb.repository.stock.VwStockRecordPersonalHistoryRepository;
import com.avp.mem.njpb.repository.stock.VwStockRecordPersonalRepository;
import com.avp.mem.njpb.repository.stock.VwStockRecordRepository;
import com.avp.mem.njpb.repository.stock.VwStockWorkOrderDetailRepository;
import com.avp.mem.njpb.util.BusinessRefData;
import com.avp.mem.njpb.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Created by six on 2017/8/17.
 */
@RestController
public class StockController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockController.class);

    @Autowired
    VwStockRecordPersonalRepository vwStockRecordPersonalRepository;

    @Autowired
    ObjStockRecordRepository objStockRecordRepository;

    @Autowired
    VwStockRecordRepository vwStockRecordRepository;

    @Autowired
    VwStockWorkOrderDetailRepository vwStockWorkOrderDetailRepository;

    @Autowired
    VwStockRecordPersonalHistoryRepository vwStockRecordPersonalHistoryRepository;

    /**
     * 查询个人库存
     *
     * @param partsType
     * @return
     */
    @RequestMapping(value = "stockRecordPersonals/findByPartsType", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwStockRecordPersonal>> findStockRecordPersonalsByPartsType(Integer partsType) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            LOGGER.debug("查询类别：{}", partsType);
            if (Validator.isNull(partsType)) {
                LOGGER.debug("参数缺失!");
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                return builder.getResponseEntity();
            }

            Integer uid = SecurityUtils.getLoginUserId();
            if (uid == null) {
                LOGGER.debug("参数缺失，请求失败");
                builder.setResponseCode(ResponseCode.FAILED);
                return builder.getResponseEntity();
            }

            List<VwStockRecordPersonal> vwStockRecordPersonals = vwStockRecordPersonalRepository.findByUserIdAndPartsType(uid, partsType);
            LOGGER.debug("个人仓库类别数：{}", vwStockRecordPersonals.size());
            builder.setResultEntity(vwStockRecordPersonals, ResponseCode.OK);
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.CREATE_FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * 查询个人库存条目
     *
     * @param estateTypeId
     * @return
     */
    @RequestMapping(value = "stockRecordPersonalHistories/findByEstateTypeId", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwStockRecordPersonalHistory>> findStockRecordPersonalHistoriesByEstateTypeId(Integer estateTypeId) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            if (Validator.isNull(estateTypeId)) {
                LOGGER.debug("参数缺失!");
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                return builder.getResponseEntity();
            }

            Integer uid = SecurityUtils.getLoginUserId();

            List<VwStockRecordPersonalHistory> vwStockRecordPersonalHistories = vwStockRecordPersonalHistoryRepository.findByEstateTypeIdAndUserIdOrderByOperationTimeDesc(estateTypeId, uid);

            LOGGER.debug("个人仓库查询指定设备类型历史数据成功，数据条目为：{}", vwStockRecordPersonalHistories.size());

            builder.setResultEntity(vwStockRecordPersonalHistories, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * 根据公司查询大仓库
     *
     * @param corpId
     * @return
     */
    @RequestMapping(value = "stockRecords/findByCorpId", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwStockRecordPersonal>> findByCorpId(Integer corpId) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            if (Validator.isNull(corpId)) {
                LOGGER.debug("参数缺失!");
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                return builder.getResponseEntity();
            }

            List<ObjStockRecord> objStockRecords = objStockRecordRepository.findByCorpId(corpId);

            LOGGER.debug("大仓库数据量为：{}", objStockRecords.size());
            builder.setResultEntity(objStockRecords, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * 查询大仓库
     *
     * @param corpId
     * @return
     */
    @RequestMapping(value = "stockRecords/findConditions", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwStockRecord>> findAll(Integer corpId, Integer category, Integer estateTypeId, Pageable page) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {

            Page<VwStockRecord> list = vwStockRecordRepository.findAll(where(queryStockRecordsByConditions(corpId, category, estateTypeId)), page);

            if (list.getTotalElements() <= 0) {
                LOGGER.debug("查询库存数据成功，结果为空");
            } else {
                LOGGER.debug("查询库存数据成功，数据量为:({})", list.getTotalElements());
            }
            builder.setResultEntity(list, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    // Dynamic Query Utils
    public Specification<VwStockRecord> queryStockRecordsByConditions(Integer corpId, Integer category, Integer estateTypeId) {
        return new Specification<VwStockRecord>() {
            public Predicate toPredicate(Root<VwStockRecord> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Predicate predicate = builder.conjunction();

                LOGGER.debug("stockRecords/findAll请求的参数corpId值为:{}", corpId);
                if (corpId != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwStockRecord_.corpId), corpId));
                }
                LOGGER.debug("stockRecords/findAll请求的参数category值为:{}", category);
                if (category != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwStockRecord_.category), category));
                }

                LOGGER.debug("stockRecords/findAll请求的参数estateTypeId值为:{}", estateTypeId);
                if (estateTypeId != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwStockRecord_.estateTypeId), estateTypeId));
                }
                predicate.getExpressions().add(builder.isNull(root.get(VwStockRecord_.removeTime)));

                return predicate;
            }
        };
    }
    // Dynamic End

    /**
     * 大仓库数据新建
     *
     * @param objStockRecord
     * @return
     */
    @PostMapping(value = "stockRecords")
    ResponseEntity<RestBody<VwStockRecordPersonal>> saveStockRecord(@RequestBody ObjStockRecord objStockRecord) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            ObjStockRecord objStockRecordT = new ObjStockRecord();
            ObjStockRecord objStockRecordTemp = objStockRecordRepository.findByEstateTypeIdAndCorpId(objStockRecord.getEstateTypeId(), objStockRecord.getCorpId());
            if (Validator.isNotNull(objStockRecordTemp)) {
                objStockRecordTemp.setCount(objStockRecord.getCount()+objStockRecordTemp.getCount());
                objStockRecordT = objStockRecordRepository.save(objStockRecordTemp);
            }else{
                objStockRecordT = objStockRecordRepository.save(objStockRecord);
            }


            // TODO: 2018/2/1  pc端入库时，添加一条工单；
            LOGGER.debug("大仓库数据保存成功");
            builder.setResultEntity(objStockRecordT, ResponseCode.CREATE_SUCCEED);
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.CREATE_FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * 查询大仓库出入库记录
     *
     * @param corpId
     * @return
     */
    @RequestMapping(value = "stockWorkOrderDetails/findConditions", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwStockWorkOrderDetail>> findAll(Integer corpId, Integer category, Integer estateTypeId, Integer stockWorkOrderTypeId, Pageable page) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {

            Page<VwStockWorkOrderDetail> list = vwStockWorkOrderDetailRepository.findAll(where(queryStockWorkOrderDetailsByConditions(corpId, category, estateTypeId, stockWorkOrderTypeId)), page);

            LOGGER.debug("查询库存数据成功，数据量为:({})", list.getTotalElements());

            builder.setResultEntity(list, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    // Dynamic Query Utils
    public Specification<VwStockWorkOrderDetail> queryStockWorkOrderDetailsByConditions(Integer corpId, Integer category, Integer estateTypeId, Integer stockWorkOrderTypeId) {
        return new Specification<VwStockWorkOrderDetail>() {
            public Predicate toPredicate(Root<VwStockWorkOrderDetail> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Predicate predicate = builder.conjunction();

                LOGGER.debug("stockWorkOrderDetails/findConditions请求的参数corpId值为:{}", corpId);
                if (corpId != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwStockWorkOrderDetail_.corpId), corpId));
                }
                LOGGER.debug("stockWorkOrderDetails/findConditions请求的参数category值为:{}", category);
                if (category != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwStockWorkOrderDetail_.category), category));
                }

                LOGGER.debug("stockWorkOrderDetails/findConditions请求的参数estateTypeId值为:{}", estateTypeId);
                if (estateTypeId != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwStockWorkOrderDetail_.estateTypeId), estateTypeId));
                }

                LOGGER.debug("stockWorkOrderDetails/findConditions请求的参数stockWorkOrdeTypeId值为:{}", stockWorkOrderTypeId);
                if (stockWorkOrderTypeId != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwStockWorkOrderDetail_.stockWorkOrderTypeId), stockWorkOrderTypeId));
                }

                predicate.getExpressions().add(builder.isNull(root.get(VwStockWorkOrderDetail_.removeTime)));

                predicate.getExpressions().add(builder.equal(root.get(VwStockWorkOrderDetail_.stockWorkOrderStatusId), BusinessRefData.STOCK_WORK_ORDER_STATUS_AFFIRM));

                return predicate;
            }
        };
    }
    // Dynamic End







}
