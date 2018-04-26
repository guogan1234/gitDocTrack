/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.resource.basic;

import com.avp.mem.njpb.api.rest.ResponseBuilder;
import com.avp.mem.njpb.api.rest.ResponseCode;
import com.avp.mem.njpb.api.rest.RestBody;
import com.avp.mem.njpb.api.util.SecurityUtils;
import com.avp.mem.njpb.entity.estate.ObjBarcodeImage;
import com.avp.mem.njpb.entity.estate.ObjStation;
import com.avp.mem.njpb.entity.estate.VwObjStation;
import com.avp.mem.njpb.entity.view.VwEstate;
import com.avp.mem.njpb.entity.view.VwUserEstateBarCode;
import com.avp.mem.njpb.repository.basic.ObjImageBarCodeRepository;
import com.avp.mem.njpb.repository.estate.ObjEstateRepository;
import com.avp.mem.njpb.repository.estate.ObjStationRepository;
import com.avp.mem.njpb.repository.estate.VwEstateRepository;
import com.avp.mem.njpb.repository.estate.VwObjStationRepository;
import com.avp.mem.njpb.repository.estate.VwUserEstateBarCodeRepository;
import com.avp.mem.njpb.repository.estate.VwUserEstateBarCodeRepositorySpecification;
import com.avp.mem.njpb.service.createBarCode.BatchCreateBarCode;
import com.avp.mem.njpb.service.createBarCode.BatchCreateStationBarcode;
import com.avp.mem.njpb.util.BusinessRefData;
import com.avp.mem.njpb.util.MagicNumber;
import com.avp.mem.njpb.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Created by six on 2017/7/27.
 */
@RestController
public class BarCodeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BarCodeController.class);

    @Autowired
    VwUserEstateBarCodeRepository vwUserEstateBarCodeRepository;

    @Autowired
    VwObjStationRepository vwObjStationRepository;

    @Autowired
    ObjImageBarCodeRepository objImageBarCodeRepository;

    @Autowired
    ObjEstateRepository objEstateRepository;

    @Autowired
    ObjStationRepository objStationRepository;

    @Autowired
    VwEstateRepository vwEstateRepository;

    @Autowired
    BatchCreateBarCode batchCreateBarCode;

    @Autowired
    BatchCreateStationBarcode batchCreateStationBarcode;

    /**
     * 设备/模块二维码条件查询被使用的二维码
     *
     * @return
     */
    @RequestMapping(value = "estateBarCodes/findUsedByConditions", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwUserEstateBarCode>> findByUidAndConditions(Integer corpId,
                                                                         Integer relation,
                                                                         Integer category, Integer estateTypeId, Integer stationId, Pageable page) {
        ResponseBuilder<VwUserEstateBarCode> builder = ResponseBuilder.createBuilder();
        try {
            Integer uid = SecurityUtils.getLoginUserId();
            Page<VwUserEstateBarCode> list = vwUserEstateBarCodeRepository.findAll(where(VwUserEstateBarCodeRepositorySpecification.byConditions(corpId, relation, stationId, category, estateTypeId, uid)), page);

            LOGGER.debug("查询设备模块二维码详细数据成功，数据量为:({})", list.getTotalElements());
            builder.setResultEntity(list, ResponseCode.OK);
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * 设备/模块二维码条件查询未被使用的二维码
     *
     * @return
     */
    @RequestMapping(value = "estateBarCodes/findNotUsed", method = RequestMethod.GET)
    ResponseEntity<RestBody<ObjBarcodeImage>> findNotUsed(Pageable page) {
        ResponseBuilder<ObjBarcodeImage> builder = ResponseBuilder.createBuilder();
        try {
            Integer uid = SecurityUtils.getLoginUserId();
            Page<ObjBarcodeImage> list = objImageBarCodeRepository.findByRelation(BusinessRefData.BAR_CODE_NO_RELEVANCE, page);

            LOGGER.debug("查询空二维码数据成功，数据量为:({})", list.getTotalElements());
            builder.setResultEntity(list, ResponseCode.OK);
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * 二维码生成指定数量空码
     *
     * @param count
     * @return
     */
    @RequestMapping(value = "estateBarCodes/produceByCount", method = RequestMethod.POST)
    ResponseEntity<RestBody<ObjBarcodeImage>> createEstateBarCodes(Integer count) {
        LOGGER.debug("二维码生成指定数量({})空码", count);

        ResponseBuilder<ObjBarcodeImage> builder = ResponseBuilder.createBuilder();
        List<ObjBarcodeImage> objBarcodeImages = new ArrayList<>();
        try {
            if (count < 1) {
                LOGGER.debug("指定数量不合法，空码数量要大于0");
                builder.setResponseCode(ResponseCode.CREATE_FAILED, "指定数量不合法，空码数量要大于0");
            }
            // 生成二维码
            for (int i = 0; i < count; i++) {
                ObjBarcodeImage objBarcodeImage = new ObjBarcodeImage();
                //objBarcodeImage.setBarCodeCategory(3);
                objBarcodeImage.setRelation(BusinessRefData.BAR_CODE_NO_RELEVANCE);
                //objBarcodeImage.setExportTime(new Date());
                objBarcodeImages.add(objBarcodeImage);
            }
            objImageBarCodeRepository.save(objBarcodeImages);

            LOGGER.debug("{}个空码生成成功", count);

            builder.setResultEntity(ResponseCode.CREATE_SUCCEED);
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.CREATE_FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * 通过二维码查询设备信息(不控制数据权限)
     *
     * @param barCodeSn
     * @return
     */
    @RequestMapping(value = "estateBarCodes/findByBarCodeSn", method = RequestMethod.GET)
    ResponseEntity<RestBody> findEstateBarCode(String barCodeSn) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            if (Validator.isNull(barCodeSn)) {
                LOGGER.debug("barCodeSn参数为空，参数缺失!");
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                return builder.getResponseEntity();
            }
            VwEstate vwEstate;
            VwObjStation vwObjStation;
            int codeLength = barCodeSn.length();
            if (codeLength == MagicNumber.SIX) {
                vwObjStation = vwObjStationRepository.findByStationNo(barCodeSn);
                if (Validator.isNull(vwObjStation)) {
                    LOGGER.debug("根据二维码：{}查询数据，数据为空", barCodeSn);
                    builder.setResponseCode(ResponseCode.RETRIEVE_FAILED, "站点数据不存在");
                    return builder.getResponseEntity();

                } else {
                    builder.setResultEntity(vwObjStation, ResponseCode.OK);
                }
            }else if(codeLength == MagicNumber.EIGHT){
                vwEstate = vwEstateRepository.findByBicycleStakeBarCode(barCodeSn);
                if (Validator.isNull(vwEstate)) {
                    LOGGER.debug("根据二维码：{}查询数据，数据为空", barCodeSn);
                    builder.setResponseCode(ResponseCode.RETRIEVE_FAILED, "车桩设备数据不存在");
                    return builder.getResponseEntity();
                } else {
                    builder.setResultEntity(vwEstate, ResponseCode.OK);
                }
            }else{
                vwEstate = vwEstateRepository.findOneByEstateSn(barCodeSn);
                if (Validator.isNull(vwEstate)) {
                    LOGGER.debug("根据二维码：{}查询数据，数据为空", barCodeSn);
                    builder.setResponseCode(ResponseCode.RETRIEVE_FAILED, "设备数据不存在");
                    return builder.getResponseEntity();
                } else {
                    builder.setResultEntity(vwEstate, ResponseCode.OK);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * 通过二维码查询设备信息(控制数据权限)
     *
     * @param barCodeSn
     * @return
     */
    @RequestMapping(value = "estateBarCodes/findByBarCodeSnAndUid", method = RequestMethod.GET)
    ResponseEntity<RestBody<VwUserEstateBarCode>> findEstateByBarCodeAndUid(String barCodeSn) {
        ResponseBuilder<VwUserEstateBarCode> builder = ResponseBuilder.createBuilder();
        try {
            if (Validator.isNull(barCodeSn)) {
                LOGGER.debug("barCodeSn参数为空，参数缺失!");
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                return builder.getResponseEntity();
            }

            Integer uid = SecurityUtils.getLoginUserId();

            ObjBarcodeImage objBarcodeImage = objImageBarCodeRepository.findOneByBarCodeSn(barCodeSn);

            if (Validator.isNull(objBarcodeImage)) {
                LOGGER.debug("根据二维码：{}查询数据，数据为空", barCodeSn);
                builder.setResponseCode(ResponseCode.RETRIEVE_FAILED, "二维码数据不存在");
                return builder.getResponseEntity();
            }

            List<VwUserEstateBarCode> vwUserEstateBarCodes = vwUserEstateBarCodeRepository.findByBarCodeSnAndUid(barCodeSn, uid);

            LOGGER.debug("根据二维码：{}和用户id：{}查询数据成功,数据量为：{}", barCodeSn, uid, vwUserEstateBarCodes.size());

            builder.setResultEntity(vwUserEstateBarCodes, ResponseCode.OK);
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }


    /**
     * 查询条码空码
     *
     * @param barCodeSn
     * @return
     */
    @RequestMapping(value = "barcodeImages/findBarCodeNoRelation", method = RequestMethod.GET)
    ResponseEntity<RestBody<ObjBarcodeImage>> findBarCodeNoRelation(String barCodeSn) {
        ResponseBuilder<ObjBarcodeImage> builder = ResponseBuilder.createBuilder();
        try {
            if (Validator.isNull(barCodeSn)) {
                LOGGER.debug("barCodeSn参数为空，参数缺失!");
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                return builder.getResponseEntity();
            }

            ObjBarcodeImage objBarcodeImages = objImageBarCodeRepository.findOneByBarCodeSnAndRelation(barCodeSn, BusinessRefData.BAR_CODE_NO_RELEVANCE);

            LOGGER.debug("根据二维码：{}查询成功", barCodeSn);

            builder.setResultEntity(objBarcodeImages, ResponseCode.OK);
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.CREATE_FAILED);
        }
        return builder.getResponseEntity();
    }


    @RequestMapping(value = "barcodeImages/create", method = RequestMethod.POST)
    ResponseEntity createBarCode(HttpServletResponse response, @RequestParam(value = "ids") List<Integer> ids
    ) {
        ResponseBuilder<ObjBarcodeImage> builder = ResponseBuilder.createBuilder();
        try {
            Integer uid = SecurityUtils.getLoginUserId();
            List<VwUserEstateBarCode> list = vwUserEstateBarCodeRepository.findByUidAndIdIn(uid, ids);

            LOGGER.debug("查询设备模块二维码详细数据成功，数据量为:({})", list.size());


            batchCreateBarCode.createBarCode(response, list);

            builder.setResponseCode(ResponseCode.OK);
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.CREATE_FAILED);
        }
        return builder.getResponseEntity();
    }

    @RequestMapping(value = "stationBarcodeImages/create", method = RequestMethod.POST)
    ResponseEntity createStationBarCode(HttpServletResponse response, @RequestParam(value = "ids") List<Integer> ids) {
        ResponseBuilder<ObjBarcodeImage> builder = ResponseBuilder.createBuilder();
        try {

            List<ObjStation> objStations = objStationRepository.findByIdIn(ids);

            LOGGER.debug("查询设备模块二维码详细数据成功，数据量为:({})", objStations.size());

            batchCreateStationBarcode.createBarCode(response, objStations);
            builder.setResponseCode(ResponseCode.OK);
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.CREATE_FAILED);
        }
        return builder.getResponseEntity();
    }

    @RequestMapping(value = "barcodeImagesFirst/create", method = RequestMethod.GET)
    ResponseEntity barcodeImagesFirst() {
        ResponseBuilder<ObjBarcodeImage> builder = ResponseBuilder.createBuilder();
        try {
            batchCreateBarCode.createBarCodeFirst();
            builder.setResponseCode(ResponseCode.OK);
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.CREATE_FAILED);
        }
        return builder.getResponseEntity();
    }

    @RequestMapping(value = "stationBarcodeImagesFirst/create", method = RequestMethod.GET)
    ResponseEntity stationBarcodeImagesFirst() {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            batchCreateStationBarcode.createBarCodeFirstStation();
            builder.setResponseCode(ResponseCode.OK);
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.CREATE_FAILED);
        }
        return builder.getResponseEntity();
    }
}