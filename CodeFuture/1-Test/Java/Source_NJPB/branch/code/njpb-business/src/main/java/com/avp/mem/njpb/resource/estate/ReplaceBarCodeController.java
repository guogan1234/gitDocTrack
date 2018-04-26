/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.resource.estate;

import com.avp.mem.njpb.api.rest.ResponseBuilder;
import com.avp.mem.njpb.api.rest.ResponseCode;
import com.avp.mem.njpb.entity.estate.ObjBarcodeImage;
import com.avp.mem.njpb.entity.estate.ObjEstate;
import com.avp.mem.njpb.repository.basic.ObjImageBarCodeRepository;
import com.avp.mem.njpb.repository.estate.ObjEstateRepository;
import com.avp.mem.njpb.repository.estate.VwUserEstateRepository;
import com.avp.mem.njpb.util.BusinessRefData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by six on 2017/8/10.
 */
@RestController
public class ReplaceBarCodeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReplaceBarCodeController.class);

    @Autowired
    ObjEstateRepository objEstateRepository;

    @Autowired
    VwUserEstateRepository vwUserEstateRepository;

    @Autowired
    ObjImageBarCodeRepository objImageBarCodeRepository;

    /**
     * 更换条码
     *
     * @return
     */
    @RequestMapping(value = "estates/replaceBarCode", method = RequestMethod.PUT)
    ResponseEntity updateEstateEn(Integer estateId, String newEstateSn) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            ObjBarcodeImage objBarcodeImage = objImageBarCodeRepository.findOneByBarCodeSn(newEstateSn);
            LOGGER.debug("新的条码：{}", newEstateSn);
            if (objBarcodeImage == null) {
                LOGGER.debug("新的条码不存在，请重新扫描其他二维码");
                builder.setResponseCode(ResponseCode.NOT_EXIST, "新的条码不存在，请重新扫描其他二维码");
                return builder.getResponseEntity();
            }
            if (objBarcodeImage.getRelation().equals(BusinessRefData.BAR_CODE_RELEVANCE)) {
                LOGGER.debug("新的条码已经关联设备，请重新扫描其他二维码");
                builder.setResponseCode(ResponseCode.NOT_EXIST, "新的条码已经关联设备，请重新扫描其他二维码");
                return builder.getResponseEntity();
            }

            ObjEstate objEstate = objEstateRepository.findOne(estateId);

            LOGGER.debug("设备：{}的原有sn为：{}", estateId, objEstate.getEstateSn());

            objEstate.setEstateSn(newEstateSn);
            objEstateRepository.save(objEstate);


            objBarcodeImage.setRelation(BusinessRefData.BAR_CODE_RELEVANCE);
            objImageBarCodeRepository.save(objBarcodeImage);

            builder.setResponseCode(ResponseCode.UPDATE_SUCCEED, "条码更换成功");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.UPDATE_FAILED);
        }
        return builder.getResponseEntity();
    }
}
