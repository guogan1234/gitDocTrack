/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.resource.estate;

import com.avp.mem.njpb.api.rest.ResponseBuilder;
import com.avp.mem.njpb.api.rest.ResponseCode;
import com.avp.mem.njpb.api.rest.RestBody;
import com.avp.mem.njpb.api.util.SecurityUtils;
import com.avp.mem.njpb.entity.estate.ObjBarcodeImage;
import com.avp.mem.njpb.entity.estate.ObjEstate;
import com.avp.mem.njpb.entity.estate.ObjEstateType;
import com.avp.mem.njpb.entity.estate.ObjStation;
import com.avp.mem.njpb.entity.view.VwEstate;
import com.avp.mem.njpb.entity.view.VwUserEstate;
import com.avp.mem.njpb.entity.view.VwUserEstate_;
import com.avp.mem.njpb.repository.basic.ObjImageBarCodeRepository;
import com.avp.mem.njpb.repository.estate.ObjEstateRepository;
import com.avp.mem.njpb.repository.estate.ObjStationRepository;
import com.avp.mem.njpb.repository.estate.VwEstateRepository;
import com.avp.mem.njpb.repository.estate.VwUserEstateRepository;
import com.avp.mem.njpb.util.BusinessRefData;
import com.avp.mem.njpb.util.DateConverter;
import com.avp.mem.njpb.util.MagicNumber;
import com.avp.mem.njpb.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.PathVariable;
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
 * Created by six on 2017/7/26.
 */
@RestController
public class EstateController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EstateController.class);

    @Autowired
    ObjEstateRepository objEstateRepository;

    @Autowired
    VwUserEstateRepository vwUserEstateRepository;

    @Autowired
    VwEstateRepository vwEstateRepository;

    @Autowired
    ObjStationRepository objStationRepository;


    @Autowired
    ObjImageBarCodeRepository objImageBarCodeRepository;

    /**
     * 非车辆设备查询
     *
     * @return
     */
    @RequestMapping(value = "estates/findByConditions", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwUserEstate>> findAll(Integer corpId, Integer stationId, Integer category, Integer estateTypeId, Pageable page) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            Integer uid = SecurityUtils.getLoginUserId();
            Page<VwUserEstate> list = vwUserEstateRepository.findAll(where(byConditions(corpId, stationId, category, estateTypeId, uid)), page);

            LOGGER.debug("查询设备模块详细数据成功，数据量为:({})", list.getTotalElements());
            builder.setResultEntity(list, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    // Dynamic Query Utils
    public Specification<VwUserEstate> byConditions(Integer corpId, Integer stationId, Integer category, Integer estateTypeId, Integer uid) {
        return new Specification<VwUserEstate>() {
            public Predicate toPredicate(Root<VwUserEstate> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Predicate predicate = builder.conjunction();

                LOGGER.debug("estates/findByConditions请求的参数corpId值为:{}", corpId);
                if (corpId != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwUserEstate_.projectId), corpId));
                }

                LOGGER.debug("estates/findByConditions请求的参数stationId值为:{}", stationId);
                if (stationId != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwUserEstate_.stationId), stationId));
                }

                LOGGER.debug("estates/findByConditions请求的参数category值为:{}", category);
                if (category != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwUserEstate_.category), category));
                }

                LOGGER.debug("estates/findByConditions请求的参数estateTypeId值为:{}", estateTypeId);
                if (estateTypeId != null) {
                    predicate.getExpressions().add(builder.equal(root.get(VwUserEstate_.estateTypeId), estateTypeId));
                }

                predicate.getExpressions().add(builder.equal(root.get(VwUserEstate_.uid), uid));

                return predicate;
            }
        };
    }
    // Dynamic End

    /**
     * 车辆设备查询
     *
     * @return
     */
    @RequestMapping(value = "estates/findBikesByConditions", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwUserEstate>> findBikeByConfitions(Pageable page) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            Integer uid = SecurityUtils.getLoginUserId();
            Page<VwEstate> list = vwEstateRepository.findByCategory(BusinessRefData.ESTATE_CATEGORY_BICYCLE, page);

            LOGGER.debug("查询车辆数据成功，数据量为:({})", list.getTotalElements());
            builder.setResultEntity(list, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }


    /**
     * 车辆设备查询
     *
     * @return
     */
    @RequestMapping(value = "estates/findBikeByConditions", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwEstate>> findBikesByConfitions() {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            List<VwEstate> list = vwEstateRepository.findByCategory(BusinessRefData.ESTATE_CATEGORY_BICYCLE);

            LOGGER.debug("查询车辆数据成功，数据量为:({})", list.size());
            builder.setResultEntity(list, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * @return
     */
    @RequestMapping(value = "estates/findBikesByCategory", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwEstate>> findBikesByCategory() {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {

            VwEstate vwEstate = vwEstateRepository.findTopByCategoryOrderByBikeFrameNoDesc(BusinessRefData.ESTATE_CATEGORY_BICYCLE);
            if (Validator.isNotNull(vwEstate)) {
                builder.setResultEntity(vwEstate, ResponseCode.RETRIEVE_SUCCEED);
            } else {
                builder.setResponseCode(ResponseCode.OK, "没有自行车");
            }

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }


    // 设备新建
    @RequestMapping(value = "estates", method = RequestMethod.POST)
    @Transactional
    public ResponseEntity<RestBody<VwEstate>> buildEstate(@RequestBody ObjEstate objEstate) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            if (Validator.isNull(objEstate) || Validator.isNull(objEstate.getEstateTypeId())) {
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                LOGGER.debug("设备新建参数缺失，请求失败");
                return builder.getResponseEntity();
            }


            if (objEstate.getEstateTypeId() == MagicNumber.ONE) {
                ObjEstate es = objEstateRepository.findTopByEstateTypeIdAndProjectIdAndStationIdOrderByEstateNoDesc(objEstate
                        .getEstateTypeId(), objEstate.getProjectId(), objEstate.getStationId());

                if (Validator.isNotNull(es)) {

                    String bicycleStakeBarCode = es.getBicycleStakeBarCode();

                    Integer bicycleStakeBarCode2 = Integer.parseInt(bicycleStakeBarCode) + 1;
                    objEstate.setBicycleStakeBarCode("0" + bicycleStakeBarCode2);
                } else {
                    ObjStation objStation = objStationRepository.findOne(objEstate.getStationId());

                    objEstate.setBicycleStakeBarCode(objStation.getStationNo() + "01");
                }
                Integer estateNo;
                if (Validator.isNull(es)) {
                    estateNo = BusinessRefData.FIRST_ESTATE_NO;
                } else {
                    if (Validator.isNull(es.getEstateNo())) {
                        estateNo = BusinessRefData.FIRST_ESTATE_NO;
                    } else {
                        estateNo = es.getEstateNo() + 1;
                    }
                }
                LOGGER.debug("下一个设备编号为：{}", estateNo);

                String estateBatch = DateConverter.doConvertToDateString(new Date());
                // save estate no
                objEstate.setEstateNo(estateNo);
                objEstate.setEstateBatch(estateBatch);

                // 保存后才能得到logicalId
                ObjEstate estate = objEstateRepository.save(objEstate);

                // save path,新建均是根节点
                estate.setEstatePath(estate.getLogicalId().toString());
                estate = objEstateRepository.save(estate);
                LOGGER.debug("设备：{}新建成功", estate.getEstateName());


            } else {
                Integer corpId = objEstate.getProjectId();

                // get estate no(设备序号)
                ObjEstate es = objEstateRepository.findTopByEstateTypeIdAndProjectIdAndStationIdOrderByEstateNoDesc(objEstate
                        .getEstateTypeId(), corpId, objEstate.getStationId());
            /*设置二维码*/
                if (Validator.isNotNull(es)) {
                    String estateSn = "";
                    String estateSnTemp = es.getEstateSn();
                    String estateTypeSn = estateSnTemp.substring(0, MagicNumber.EIGHT);
                    Integer estateCount = Integer.parseInt(estateSnTemp.substring(MagicNumber.EIGHT, MagicNumber.NINE));
                    Integer typeId = es.getEstateTypeId();
                    if (typeId == MagicNumber.TWO) {
                        estateSn = estateTypeSn + 2 + (estateCount + 1);
                    } else if (typeId == MagicNumber.SIX) {
                        estateSn = estateTypeSn + 1 + (estateCount + 1);
                    } else if (typeId == MagicNumber.TWO_ONE) {
                        estateSn = estateTypeSn + MagicNumber.THREE + (estateCount + 1);
                    } else if (typeId == MagicNumber.SEVEN_NINE) {
                        estateSn = estateTypeSn + MagicNumber.FOUR + (estateCount + 1);
                    } else {
                        estateSn = estateTypeSn + typeId + (estateCount + 1);
                    }
                    objEstate.setEstateSn(estateSn);

                /*绑定二维码表*/
                    ObjBarcodeImage objImageBarCode = new ObjBarcodeImage();
                    objImageBarCode.setRelation(BusinessRefData.BAR_CODE_RELEVANCE);
                    objImageBarCode.setBarCodeSn(estateSn);
                    objImageBarCode = objImageBarCodeRepository.save(objImageBarCode);


                    Integer estateNo;
                    if (Validator.isNull(es)) {
                        estateNo = BusinessRefData.FIRST_ESTATE_NO;
                    } else {
                        if (Validator.isNull(es.getEstateNo())) {
                            estateNo = BusinessRefData.FIRST_ESTATE_NO;
                        } else {
                            estateNo = es.getEstateNo() + 1;
                        }
                    }
                    LOGGER.debug("下一个设备编号为：{}", estateNo);

                    String estateBatch = DateConverter.doConvertToDateString(new Date());
                    // save estate no
                    objEstate.setEstateNo(estateNo);
                    objEstate.setEstateBatch(estateBatch);
                    // 保存后才能得到logicalId
                    ObjEstate estate = objEstateRepository.save(objEstate);
                    estate.setEstatePath(estate.getLogicalId().toString());
                    ObjEstate estate1 = objEstateRepository.save(estate);
                    LOGGER.debug("设备：{}新建成功", estate1.getEstateName());
                } else {
                    ObjStation objStation = objStationRepository.findOne(objEstate.getStationId());
                    Integer typeId = objEstate.getEstateTypeId();
                    String estateSnTemp = "";
                    if (typeId == MagicNumber.TWO) {
                        estateSnTemp = "201";
                    } else if (typeId == MagicNumber.SIX) {
                        estateSnTemp = "101";
                    } else if (typeId == MagicNumber.TWO_ONE) {
                        estateSnTemp = "301";
                    } else if (typeId == MagicNumber.SEVEN_NINE) {
                        estateSnTemp = "401";
                    } else {
                        estateSnTemp = typeId + "01";
                    }

                    String estateSn = objStation.getStationNo() + estateSnTemp;
                    objEstate.setEstateSn(estateSn);

                    /*绑定二维码表*/
                    ObjBarcodeImage objImageBarCode = new ObjBarcodeImage();
                    objImageBarCode.setRelation(BusinessRefData.BAR_CODE_RELEVANCE);
                    objImageBarCode.setBarCodeSn(estateSn);
                    objImageBarCode = objImageBarCodeRepository.save(objImageBarCode);

                    Integer estateNo = BusinessRefData.FIRST_ESTATE_NO;

                    LOGGER.debug("下一个设备编号为：{}", estateNo);

                    String estateBatch = DateConverter.doConvertToDateString(new Date());
                    // save estate no
                    objEstate.setEstateNo(estateNo);
                    objEstate.setEstateBatch(estateBatch);

                    // 保存后才能得到logicalId
                    ObjEstate estate = objEstateRepository.save(objEstate);

                    // save path,新建均是根节点
                    estate.setEstatePath(estate.getLogicalId().toString());
                    estate = objEstateRepository.save(estate);
                    LOGGER.debug("设备：{}新建成功", estate.getEstateName());
                }
            }

            builder.setResponseCode(ResponseCode.CREATE_SUCCEED);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            builder.setResponseCode(ResponseCode.CREATE_FAILED);
        }
        return builder.getResponseEntity();
    }


    /**
     * @return
     */
    @RequestMapping(value = "estates/findStationIdAndEstateName", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwEstate>> findBikesByCategory(Integer stationId, String estateName) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            if (Validator.isNull(estateName)) {
                builder.setResponseCode(ResponseCode.OK, "没有此设备");

            } else {
                List<VwEstate> vwEstates = vwEstateRepository.findOneByEstateNameAndStationId(estateName, stationId);
                if (Validator.isNotNull(vwEstates)) {
                    builder.setResultEntity(vwEstates, ResponseCode.RETRIEVE_SUCCEED);
                } else {
                    builder.setResponseCode(ResponseCode.OK, "没有此设备");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }


    // 设备新建
    @RequestMapping(value = "/bikes", method = RequestMethod.POST)
    @Transactional
    public ResponseEntity<RestBody<VwEstate>> batchBuildEstate(@RequestBody ObjEstate objEstate, Integer count) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        List<ObjEstate> objEstates = new ArrayList<>();
        try {
            if (Validator.isNull(objEstate) || Validator.isNull(objEstate.getEstateTypeId())) {
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                LOGGER.debug("设备新建参数缺失，请求失败");
                return builder.getResponseEntity();
            }

            Integer bikeFrameNo1 = objEstate.getBikeFrameNo();
            Integer bikeFrameNo2 = bikeFrameNo1 + count;

            LOGGER.debug("车架号开始值：{},创建数量为：{},车架号最大值：{}", bikeFrameNo1, count, bikeFrameNo2);

            int countDb = objEstateRepository.countByBikeFrameNoBetween(bikeFrameNo1, bikeFrameNo2);

            if (countDb > 0) {
                Integer maxCardId = objEstateRepository.findTopByOrderByBikeFrameNoDesc().getBikeFrameNo();

                LOGGER.debug("数据库中的车架号和要生成的有冲突,数据库中最大车架号是：{}", maxCardId);
                builder.setResponseCode(ResponseCode.CREATE_FAILED, "待生成的车架号和库中已有车架号冲突，库中最大车架号为：" + maxCardId);

                return builder.getResponseEntity();
            }
            // get estate no(设备序号)
            ObjEstate es = objEstateRepository.findTopByEstateTypeIdOrderByEstateNoDesc(objEstate
                    .getEstateTypeId());

            Integer estateNo;
            if (Validator.isNull(es)) {
                estateNo = BusinessRefData.FIRST_ESTATE_NO;
            } else {
                if (Validator.isNull(es.getEstateNo())) {
                    estateNo = BusinessRefData.FIRST_ESTATE_NO;
                } else {
                    estateNo = es.getEstateNo() + 1;
                }
            }
            String estateBatch = DateConverter.doConvertToDateString(new Date());

            for (int i = 0; i < count; i++) {
                ObjEstate objEstateSave = new ObjEstate();
                objEstateSave.setEstateTypeId(objEstate.getEstateTypeId());
                objEstateSave.setSupplierId(objEstate.getSupplierId());
                objEstateSave.setBikeFrameNo(objEstate.getBikeFrameNo());

                estateNo++;
                Integer nowBikeFrameNo = bikeFrameNo1++;
                LOGGER.debug("下一个设备编号为：{},下一个车架号为：{}", estateNo, nowBikeFrameNo);

                // save estate no
                objEstateSave.setEstateName("自行车" + nowBikeFrameNo);
                objEstateSave.setEstateNo(estateNo);
                objEstateSave.setBikeFrameNo(nowBikeFrameNo);
                objEstateSave.setEstateBatch(estateBatch);
                objEstateSave.setCategory(BusinessRefData.ESTATE_CATEGORY_BICYCLE);

                objEstates.add(objEstateSave);
            }

            objEstateRepository.save(objEstates);

            LOGGER.debug("车辆批量新建成功，新建数量为：{}", count);

            builder.setResponseCode(ResponseCode.CREATE_SUCCEED);
        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            builder.setResponseCode(ResponseCode.CREATE_FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * 设备/模块编辑
     *
     * @param id
     * @param objEstate
     * @return
     */
    @RequestMapping(value = "estates/{id}", method = RequestMethod.PUT)
    ResponseEntity<RestBody<VwEstate>> editEstate(@PathVariable("id") int id, @RequestBody ObjEstate objEstate) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            LOGGER.debug("estate：id为({})", id);
            Integer uid = SecurityUtils.getLoginUserId();
            objEstate.setCreateBy(uid);
            Boolean isExists = objEstateRepository.exists(id);
            if (isExists) {
                objEstate.setId(id);
                ObjEstate estate = objEstateRepository.save(objEstate);
                builder.setResultEntity(estate, ResponseCode.UPDATE_SUCCEED);
                LOGGER.debug("序号为({})对应的设备/模块编辑成功", id);
            } else {
                LOGGER.debug("estates：id为({})对应的设备/模块不存在", id);
                builder.setResponseCode(ResponseCode.NOT_EXIST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.UPDATE_FAILED);
        }
        return builder.getResponseEntity();
    }

    // 设备新建时车辆车架号查重
    @RequestMapping(value = "estates/checkBikeFrameNo", method = RequestMethod.GET)
    ResponseEntity<ObjEstateType> checkBikeFrameNo(Integer bikeFrameNo) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            if (Validator.isNull(bikeFrameNo)) {
                LOGGER.debug("参数缺失，请求失败");
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                return builder.getResponseEntity();
            }

            ObjEstate objEstate = objEstateRepository.findByBikeFrameNo(bikeFrameNo);
            if (Validator.isNull(objEstate)) {
                LOGGER.debug("objEstate为空,数据库不存在该芯片");
                builder.setResponseCode(ResponseCode.RETRIEVE_SUCCEED);
            } else {
                LOGGER.debug("根据芯片id：{}查询设备，数据库已经存在", bikeFrameNo);
                builder.setResponseCode(ResponseCode.ALREADY_EXIST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.UPDATE_FAILED);
        }

        return builder.getResponseEntity();
    }

    // 设备新建时车辆车架号查重
    @RequestMapping(value = "estates/checkEstateSn", method = RequestMethod.GET)
    ResponseEntity<ObjEstateType> checkEstateSn(String estateSn) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            if (Validator.isNull(estateSn)) {
                LOGGER.debug("参数缺失，请求失败");
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                return builder.getResponseEntity();
            }

            ObjEstate objEstate = objEstateRepository.findByEstateSnAndCategory(estateSn, BusinessRefData.ESTATE_CATEGORY_BICYCLE);
            if (Validator.isNull(objEstate)) {
                LOGGER.debug("objEstate为空,数据库不存在该车架号");
                builder.setResponseCode(ResponseCode.RETRIEVE_SUCCEED);
            } else {
                LOGGER.debug("根据车架号：{}查询自行车设备，数据库已经存在", estateSn);
                builder.setResponseCode(ResponseCode.ALREADY_EXIST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.UPDATE_FAILED);
        }

        return builder.getResponseEntity();
    }

    // 设备编辑时车辆车架号查重
    @RequestMapping(value = "estates/checkBikeFrameNoAndIdNot", method = RequestMethod.GET)
    ResponseEntity<ObjEstateType> checkCardIdAndIdNot(Integer bikeFrameNo, Integer estateId) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            if (Validator.isNull(bikeFrameNo) || Validator.isNull(estateId)) {
                LOGGER.debug("参数缺失，请求失败");
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                return builder.getResponseEntity();
            }

            ObjEstate objEstate = objEstateRepository.findByBikeFrameNoAndIdNot(bikeFrameNo, estateId);
            if (Validator.isNull(objEstate)) {
                LOGGER.debug("objEstate为空,数据库不存在该芯片id");
                builder.setResponseCode(ResponseCode.RETRIEVE_SUCCEED);
            } else {
                LOGGER.debug("根据芯片id：{}查询设备，数据库已经存在", bikeFrameNo);
                builder.setResponseCode(ResponseCode.ALREADY_EXIST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.UPDATE_FAILED);
        }

        return builder.getResponseEntity();
    }


    /**
     * 根据设备类型查询数据(有权限数据)
     *
     * @param estateTypeId
     * @return
     */
    @RequestMapping(value = "estates/findByEstateTypeIdAndUid", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwUserEstate>> findByEstateTypeIdAndUid(@RequestParam Integer estateTypeId) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            Integer uid = SecurityUtils.getLoginUserId();

            if (Validator.isNull(estateTypeId)) {
                LOGGER.debug("estateTypeId参数缺失");
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                return builder.getResponseEntity();
            }

            List<VwUserEstate> workOrderTypes = vwUserEstateRepository.findByEstateTypeIdAndUid(estateTypeId, uid);

            LOGGER.debug("根据设备类型：{}和用户id：{}查询设备数据，数据量为：{}", estateTypeId, uid, workOrderTypes.size());

            builder.setResultEntity(workOrderTypes, ResponseCode.RETRIEVE_SUCCEED);
            return builder.getResponseEntity();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * 根据设备类型查询数据(没有权限数据)
     *
     * @param estateTypeId
     * @return
     */
    @RequestMapping(value = "estates/findByEstateTypeIdAndCorpId", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwUserEstate>> findByEstateTypeIdAndCorpId(@RequestParam Integer estateTypeId, @RequestParam Integer corpId, @RequestParam Integer stationId) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            if (Validator.isNull(estateTypeId) || Validator.isNull(corpId) || Validator.isNull(stationId)) {
                LOGGER.debug("参数缺失");
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                return builder.getResponseEntity();
            }

            List<ObjEstate> objEstates = objEstateRepository.findByEstateTypeIdAndProjectIdAndStationId(estateTypeId, corpId, stationId);

            LOGGER.debug("根据设备类型：{}和公司id：{}查询设备数据，数据量为：{}", estateTypeId, corpId, objEstates.size());

            builder.setResultEntity(objEstates, ResponseCode.RETRIEVE_SUCCEED);
            return builder.getResponseEntity();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }


    /**
     * 根据车辆RFID查询数据
     *
     * @param bikeFrameNo
     * @return
     */
    @RequestMapping(value = "estates/findByBikeFrameNo", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwEstate>> findByBikeFrameNo(Integer bikeFrameNo) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            if (Validator.isNull(bikeFrameNo)) {
                LOGGER.debug("参数缺失");
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                return builder.getResponseEntity();
            }
            VwEstate objEstate = vwEstateRepository.findByBikeFrameNo(bikeFrameNo);

            if (Validator.isNull(objEstate)) {
                builder.setResponseCode(ResponseCode.NOT_EXIST, "车辆信息不存在");
            } else {
                LOGGER.debug("根据车辆芯片ID查询车辆成功，数据ID为：{}", objEstate.getId());
                builder.setResultEntity(objEstate, ResponseCode.RETRIEVE_SUCCEED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * 更新设备表数据
     *
     * @return
     */
    @Transactional
    @RequestMapping(value = "estates/updateSn", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwEstate>> updateSn() {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            List<Integer> estateTypeIds = new ArrayList<>();

            estateTypeIds.add(MagicNumber.SIX);
            estateTypeIds.add(MagicNumber.TWO_ONE);

            List<VwEstate> objEstates = vwEstateRepository.findByEstateTypeIdIn(estateTypeIds);
            LOGGER.debug("设备数量：{}", objEstates.size());
            for (int i = 0; i < objEstates.size(); i++) {
                VwEstate objEstate1 = objEstates.get(i);

                String type = "";

                if (objEstate1.getEstateTypeId() == MagicNumber.SIX) {
                    type = 1 + "";
                } else {
                    type = MagicNumber.THREE + "";
                }

                String estateSn = objEstate1.getStationNo() + type + "01";
                String t = objEstate1.getEstateTypeName() + "01";
                ObjEstate objEstate = objEstateRepository.findOne(objEstate1.getId());
                objEstate.setEstateName(t);
                objEstate.setEstateSn(estateSn);
                objEstateRepository.save(objEstate);

            }


        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * 更新二维码表数据
     *
     * @return
     */
    @Transactional
    @RequestMapping(value = "barcode/updateSn", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwEstate>> updatebarSn() {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            List<Integer> estateTypeIds = new ArrayList<>();
            estateTypeIds.add(2);
            estateTypeIds.add(MagicNumber.SIX);
            estateTypeIds.add(MagicNumber.TWO_ONE);

            List<VwEstate> objEstates = vwEstateRepository.findByEstateTypeIdIn(estateTypeIds);

            LOGGER.debug("设备数量：{}", objEstates.size());
            for (int i = 0; i < objEstates.size(); i++) {

                ObjBarcodeImage objBarcodeImage = new ObjBarcodeImage();
                VwEstate objEstate1 = objEstates.get(i);

                LOGGER.debug("设备二维码为：{}", objEstate1.getEstateSn());

                objBarcodeImage.setRelation(1);
                objBarcodeImage.setBarCodeSn(objEstate1.getEstateSn());

                objImageBarCodeRepository.save(objBarcodeImage);
            }


        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }


    /**
     * 批量新建站点设备-站点
     *
     * @return
     */
    @Transactional
    @RequestMapping(value = "estates/stations", method = RequestMethod.POST)
    ResponseEntity<RestBody<ObjEstate>> createStation() {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            List<ObjStation> objStations = objStationRepository.findAll();
            int t1 = MagicNumber.MILLION;
            for (int i = 0; i < objStations.size(); i++) {
                ObjStation objStation = objStations.get(i);
                ObjEstate objEstate = new ObjEstate();
                objEstate.setEstateTypeId(MagicNumber.SEVEN_NINE);
                objEstate.setStationId(objStation.getId());
                objEstate.setCategory(0);
                objEstate.setEstateName("站点01");
                objEstate.setEstateStatusId(1);
                objEstate.setProjectId(objStation.getProjectId());
                objEstate.setEstateSn(objStation.getStationNo() + "401");
                objEstate.setEstateNo(t1++);
                objEstateRepository.save(objEstate);
            }


        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * @param stationId
     * @return
     */
    @RequestMapping(value = "estates/findByBikeCount", method = RequestMethod.GET)
    ResponseEntity findByBikeCount(Integer stationId) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            if (Validator.isNull(stationId)) {
                LOGGER.debug("参数缺失");
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                return builder.getResponseEntity();
            }
            List<VwEstate> objEstates = vwEstateRepository.findByEstateTypeIdAndStationId(1, stationId);

            if (Validator.isNull(objEstates)) {
                builder.setResponseCode(ResponseCode.NOT_EXIST, "没有车桩消息");
            } else {
                Integer t = objEstates.size();
                builder.setResultEntity(t, ResponseCode.RETRIEVE_SUCCEED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    @RequestMapping(value = "estates/findByDay", method = RequestMethod.GET)
    ResponseEntity findByBikeCount() {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            List<ObjStation> objStations = objStationRepository.findAll();

            for (int i = 0; i < objStations.size(); i++) {
                ObjStation t = objStations.get(i);
                List<ObjEstate> objEstates = objEstateRepository.findByEstateTypeIdAndProjectIdAndStationId(MagicNumber.SIX, t.getProjectId(), t.getId());
                if (objEstates.size() > 1) {
                    ObjEstate objEstate = objEstates.get(1);
                    objEstate.setRemoveTime(new Date());
                    objEstateRepository.save(objEstate);
                    LOGGER.debug("" + objEstate.getId());
                }
            }
            builder.setResponseCode(ResponseCode.OK);

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }


}
