/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.resource.stock;

import com.avp.mem.njpb.api.rest.ResponseBuilder;
import com.avp.mem.njpb.api.rest.ResponseCode;
import com.avp.mem.njpb.api.rest.RestBody;
import com.avp.mem.njpb.api.util.SecurityUtils;
import com.avp.mem.njpb.entity.entityBO.ObjInventoryRecordBO;
import com.avp.mem.njpb.entity.estate.ObjBarcodeImage;
import com.avp.mem.njpb.entity.estate.ObjEstate;
import com.avp.mem.njpb.entity.estate.ObjEstateType;
import com.avp.mem.njpb.entity.stock.ObjInventoryRecord;
import com.avp.mem.njpb.entity.stock.ObjInventoryRecordDetail;
//import com.avp.mem.njpb.entity.stock.ObjInventoryRecord_;
import com.avp.mem.njpb.entity.stock.ObjInventoryRecord_;
import com.avp.mem.njpb.entity.system.SysUser;
import com.avp.mem.njpb.entity.view.VwInventoryRecordDetail;
import com.avp.mem.njpb.repository.basic.ObjEstateTypeRepository;
import com.avp.mem.njpb.repository.basic.ObjImageBarCodeRepository;
import com.avp.mem.njpb.repository.estate.ObjEstateRepository;
import com.avp.mem.njpb.repository.estate.VwUserEstateRepository;
import com.avp.mem.njpb.repository.stock.ObjInventoryRecordDetailRepository;
import com.avp.mem.njpb.repository.stock.ObjInventoryRecordRepository;
import com.avp.mem.njpb.repository.stock.VwInventoryRecordDetailRepository;
import com.avp.mem.njpb.repository.sys.SysUserRepository;
import com.avp.mem.njpb.repository.sys.SysUserRoleRepository;
import com.avp.mem.njpb.util.BusinessRefData;
import com.avp.mem.njpb.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Created by six on 2017/8/8.
 */
@RestController
public class InStockController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InStockController.class);


    @Autowired
    ObjInventoryRecordRepository objInventoryRecordRepository;

    @Autowired
    ObjImageBarCodeRepository objImageBarCodeRepository;

    @Autowired
    ObjEstateRepository objEstateRepository;

    @Autowired
    ObjEstateTypeRepository objEstateTypeRepository;

    @Autowired
    ObjInventoryRecordDetailRepository objInventoryRecordDetailRepository;

    @Autowired
    SysUserRepository sysUserRepository;

    @Autowired
    SysUserRoleRepository sysUserRoleRepository;

    @Autowired
    VwUserEstateRepository vwUserEstateRepository;

    @Autowired
    VwInventoryRecordDetailRepository vwInventoryRecordDetailRepository;

    /**
     * 入库
     *
     * @param objInventoryRecordBOs
     * @return
     */
    @Deprecated
    @RequestMapping(value = "inventories/inStock", method = RequestMethod.POST)
    @Transactional
    public ResponseEntity saveObjInventoryRecord(@RequestBody List<ObjInventoryRecordBO> objInventoryRecordBOs) {

        ResponseBuilder builder = ResponseBuilder.createBuilder();
        List<ObjInventoryRecord> objInventoryRecords = new ArrayList<>();
        try {
            Integer uid = SecurityUtils.getLoginUserId();
            SysUser sysUser = sysUserRepository.findOne(uid);
            LOGGER.debug("当前登录账户：{}", sysUser.getUserAccount());

            for (ObjInventoryRecordBO temp : objInventoryRecordBOs) {
                //添加入库记录
                Integer count = temp.getBarCodes().size();
                Integer corpId = sysUser.getCorpId();
                Integer estateTypeId = temp.getEstateTypeId();

                ObjInventoryRecord objInventoryRecord = new ObjInventoryRecord();
                objInventoryRecord.setCorpId(corpId);
                objInventoryRecord.setCount(count);
                objInventoryRecord.setOperator(uid);
                objInventoryRecord.setEstateTypeId(estateTypeId);
                objInventoryRecord.setOperationType(BusinessRefData.OPERATION_TYPE_IN_STOCK);
                objInventoryRecord.setStockId(corpId);

                objInventoryRecord = objInventoryRecordRepository.save(objInventoryRecord);
                LOGGER.debug("入库记录添加成功,入库记录id：{},公司id：{},数量为：{}，设备类型为：{}", objInventoryRecord.getId(), corpId, count, estateTypeId);

                ObjEstateType objEstateType = objEstateTypeRepository.findOne(estateTypeId);
                LOGGER.debug("根据设备类型id：{}查询设备类型，名称为：{},类别为：{}", estateTypeId, objEstateType.getName(), objEstateType.getCategory());

                ObjEstate objEstates = objEstateRepository.findTopByProjectIdOrderByEstateNoDesc(corpId);
                LOGGER.debug("根据设备类型id：{}查询设备类型，名称为：{},类别为：{}公司id：{},", estateTypeId, objEstateType.getName(), objEstateType.getCategory(), corpId);
                Integer estateNo;
                if (objEstates == null) {
                    estateNo = 0;
                } else {
                    if (objEstates.getEstateNo() != null) {
                        LOGGER.debug("当前公司:{}同种设备类型:{}最大设备编号为：{}", corpId, estateTypeId, objEstates.getEstateNo());
                        estateNo = objEstates.getEstateNo();
                    } else {
                        estateNo = 0;
                    }
                }
                for (String barcode : temp.getBarCodes()) {
                    ObjBarcodeImage objImageBarCode = objImageBarCodeRepository.findOneByBarCodeSn(barcode);
                    ObjEstate objEstate = new ObjEstate();
                    estateNo++;
                    //判断是否已经关联设备，如果关联，就是归还
                    if (BusinessRefData.BAR_CODE_RELEVANCE.equals(objImageBarCode.getRelation())) {
                        LOGGER.debug("该二维码号码已经和设备关联，所以执行归还操作：{}", barcode);
                        objEstate = objEstateRepository.findByEstateSn(objImageBarCode.getBarCodeSn());
                        if (Validator.isNull(objEstate)) {
                            LOGGER.debug("二维码：{}存在关联关系，但是设备表找不到指定设备,脏数据", barcode);
                            builder.setResponseCode(ResponseCode.FAILED, "二维码:" + barcode + "已被使用,但库中不存在!");
                            throw new RuntimeException("二维码:" + barcode + "已被使用,但库中不存在!");
                        }

                        objEstate.setEstateStatusId(BusinessRefData.ESTATE_IN_STOCK);
                        objEstateRepository.save(objEstate);
                    } else {
                        //更改二维码状态
                        objImageBarCode.setRelation(BusinessRefData.BAR_CODE_RELEVANCE);
                        objImageBarCode.setBarCodeCategory(objEstateType.getCategory());
                        objImageBarCodeRepository.save(objImageBarCode);
                        //更改二维码状态
                        LOGGER.debug("二维码状态更改成功,二维码号码：{}", barcode);

                        //新建设备
                        objEstate.setCategory(objEstateType.getCategory());
                        objEstate.setEstateName(objEstateType.getName() + estateNo);
                        objEstate.setEstateStatusId(BusinessRefData.ESTATE_IN_STOCK);
                        objEstate.setEstateNo(estateNo);
                        objEstate.setEstateSn(barcode);
                        //objEstate.setEstateTypeId(objInventoryRecord.getEstateTypeId());
                        objEstate.setProjectId(corpId);
                        objEstate = objEstateRepository.save(objEstate);
                        //添加设备

                        objEstate.setEstatePath(objEstate.getLogicalId() + "");
                        objEstate = objEstateRepository.save(objEstate);
                        LOGGER.debug("设备添加成功,设备id为：{}", objEstate.getId());
                    }

                    //记录详情
                    ObjInventoryRecordDetail objInventoriesRecordDetail = new ObjInventoryRecordDetail();
                    objInventoriesRecordDetail.setEstateId(objEstate.getId());
                    objInventoriesRecordDetail.setInventoryRecordId(objInventoryRecord.getId());
                    objInventoriesRecordDetail = objInventoryRecordDetailRepository.save(objInventoriesRecordDetail);
                    //关联记录跟设备关系
                    LOGGER.debug("关联记录跟设备成功,详情id为：{}", objInventoriesRecordDetail.getId());
                }

                objInventoryRecords.add(objInventoryRecord);
            }

            builder.setResultEntity(objInventoryRecords, ResponseCode.OK);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            if (builder.getResponseEntity().getStatusCode() == null) {
                builder.setResponseCode(ResponseCode.FAILED, "入库失败");
            }
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return builder.getResponseEntity();
    }

    /**
     * 出入库记录明细--web
     *
     * @param inventoryId
     * @return
     */
    @RequestMapping(value = "inventoryDetails/findByInventoryId", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwInventoryRecordDetail>> findObjInventoryRecordDetail(@RequestParam Integer inventoryId) {

        ResponseBuilder builder = ResponseBuilder.createBuilder();
        Integer uid = SecurityUtils.getLoginUserId();
        SysUser sysUser = sysUserRepository.findOne(uid);
        try {
            Boolean isExists = objInventoryRecordRepository.exists(inventoryId);
            if (isExists) {
                List<VwInventoryRecordDetail> vwInventoryRecordDetails = vwInventoryRecordDetailRepository.findByInventoryRecordIdAndCorpId(inventoryId, sysUser.getCorpId());
                LOGGER.debug("记录详情数据量为：{}", vwInventoryRecordDetails.size());
                builder.setResultEntity(vwInventoryRecordDetails, ResponseCode.OK);
            } else {
                LOGGER.debug("记录不存在");
                builder.setResponseCode(ResponseCode.FAILED, "记录不存在");
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * 出入库记录明细--APP
     *
     * @return
     */
    @RequestMapping(value = "inventoryDetails/findByEstateTypeIdAndOperationType", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwInventoryRecordDetail>> findByEstateTypeIdAndOperationType(@RequestParam Integer estateTypeId, @RequestParam Integer operationType) {
        LOGGER.debug("记录操作明细");
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            Integer uid = SecurityUtils.getLoginUserId();
            List<VwInventoryRecordDetail> vwInventoryRecordDetails = vwInventoryRecordDetailRepository.findByOperatorAndEstateTypeIdAndOperationType(uid, estateTypeId, operationType);
            LOGGER.debug("记录详情条数{}", vwInventoryRecordDetails.size());
            builder.setResultEntity(vwInventoryRecordDetails, ResponseCode.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.FAILED);
        }
        return builder.getResponseEntity();
    }


    /**
     * 库存管理-入库/出库记录条件查询
     *
     * @param corpId
     * @param estateTypeId
     * @param page
     * @return
     */
    @RequestMapping(value = "inventories/findByConditions", method = RequestMethod.GET)
    ResponseEntity<RestBody<ObjInventoryRecord>> findAll(Integer corpId, Integer estateTypeId, Integer operationType, Pageable page) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            Page<ObjInventoryRecord> list = objInventoryRecordRepository.findAll(where(byConditions(corpId, estateTypeId, operationType)), page);
            if (list.getTotalElements() <= 0) {
                LOGGER.debug("查询入库/出库记录成功，结果为空");
            } else {
                LOGGER.debug("查询入库/出库记录成功，数据量为:({})", list.getTotalElements());
            }
            builder.setResultEntity(list, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }


    // Dynamic Query Utils
    public Specification<ObjInventoryRecord> byConditions(Integer corpId, Integer estateTypeId, Integer operationType) {
        return new Specification<ObjInventoryRecord>() {
            public Predicate toPredicate(Root<ObjInventoryRecord> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Predicate predicate = builder.conjunction();

                LOGGER.debug("inventories/findByConditions请求的参数corpId值为:{}", corpId);
                if (corpId != null) {
                    predicate.getExpressions().add(builder.equal(root.get(ObjInventoryRecord_.corpId), corpId));
                }

                LOGGER.debug("inventories/findByConditions请求的参数estateTypeId值为:{}", estateTypeId);
                if (estateTypeId != null) {
                    predicate.getExpressions().add(builder.equal(root.get(ObjInventoryRecord_.estateTypeId), estateTypeId));
                }

                LOGGER.debug("inventories/findByConditions请求的参数operationType值为:{}", operationType);
                if (operationType != null) {
                    predicate.getExpressions().add(builder.equal(root.get(ObjInventoryRecord_.operationType), operationType));
                }

                predicate.getExpressions().add(builder.isNull(root.get(ObjInventoryRecord_.removeTime)));

                return predicate;
            }
        };
    }
    // Dynamic End


    /**
     * (上拉加载更多)-查询出/入库记录-APP
     *
     * @param operationType
     * @param firstOperationTime
     * @param estateTypeId
     * @param page
     * @return
     */
    @RequestMapping(value = "inventories/findByOperationTypeAndEstateTypeIdAndOperationTimeLessThan", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwInventoryRecordDetail>> findByOperationTypeAndEstateTypeIdAndOperationTimeLessThan(Integer operationType, Date firstOperationTime, Integer estateTypeId, Pageable page) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();

        //firstOperationTime = new Date();

        LOGGER.debug("inventories/findByOperationTimeLessThan, firstOperationTime为({}),p为({})",
                firstOperationTime, page);
        try {
            Integer uid = SecurityUtils.getLoginUserId();
            if (firstOperationTime == null) {
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                LOGGER.debug("参数缺失，请求失败");
                return builder.getResponseEntity();
            }
            LOGGER.debug("出/入库记录上拉加载更多的操作,首次操作时间 : " + firstOperationTime);
            Page<VwInventoryRecordDetail> list = vwInventoryRecordDetailRepository.findByOperationTypeAndOperatorAndEstateTypeIdAndLastUpdateTimeLessThan(operationType, uid, estateTypeId, firstOperationTime, page);
            if (list.getTotalElements() <= 0) {
                LOGGER.debug("查询出/入库记录成功，结果为空");
            } else {
                LOGGER.debug("查询出/入库记录成功，数据量为:({})", list.getTotalElements());
            }
            builder.setResultEntity(list, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * (下拉刷新)-查询出/入库记录-APP
     *
     * @param operationType
     * @param firstOperationTime
     * @param estateTypeId
     * @return
     */
    @RequestMapping(value = "inventories/findByOperationTypeAndEstateTypeIdAndOperationTimeGreaterThan", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwInventoryRecordDetail>> findByOperationTypeAndEstateTypeIdAndOperationTimeGreaterThan(Integer operationType, Date firstOperationTime, Integer estateTypeId) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        LOGGER.debug("inventories/findByOperationTimeGreaterThan, firstOperationTime为({})",
                firstOperationTime);
        try {
            Integer uid = SecurityUtils.getLoginUserId();
            if (firstOperationTime == null) {
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                LOGGER.debug("参数缺失，请求失败");
                return builder.getResponseEntity();
            }
            LOGGER.debug("出/入库记录下拉加载更多的操作,首次操作时间 : " + firstOperationTime);
            List<VwInventoryRecordDetail> list = vwInventoryRecordDetailRepository.findByOperationTypeAndOperatorAndEstateTypeIdAndLastUpdateTimeGreaterThan(operationType, uid, estateTypeId, firstOperationTime);
            if (list.size() <= 0) {
                LOGGER.debug("查询出/入库记录成功，结果为空");
            } else {
                LOGGER.debug("查询出/入库记录成功，数据量为:({})", list.size());
            }
            builder.setResultEntity(list, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }


}
