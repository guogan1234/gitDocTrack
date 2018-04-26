/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.service.estate;

import com.avp.mem.njpb.api.util.SecurityUtils;
import com.avp.mem.njpb.entity.estate.ObjBarcodeImage;
import com.avp.mem.njpb.repository.basic.ObjImageBarCodeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EstateService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EstateService.class);

    @Autowired
    private ObjImageBarCodeRepository imageBarCodeRepository;

    /*
     * 更新设备二维码中激活时间
     */
//    @Transactional(propagation = Propagation.REQUIRED)
    public void updateEstateActivateTime(Integer barcodeId) {
        LOGGER.debug("更新设备二维码{}的激活时间：{}", barcodeId, new Date());

        int uid = SecurityUtils.getLoginUserId();
        ObjBarcodeImage objImageBarCode = imageBarCodeRepository.findOneById(barcodeId);

        objImageBarCode.setLastUpdateBy(uid);
        objImageBarCode.setActivateTime(new Date());

        imageBarCodeRepository.save(objImageBarCode);

        LOGGER.debug("更新设备二维码中激活时间成功");
    }

    /*
    * 更新设备二维码中导出时间
    */
//    @Transactional(propagation = Propagation.REQUIRED)
    public void updateEstateExportTime(List<String> barcodeSns) {
        LOGGER.debug("更新设备二维码(数据量:{})的导出时间：{}", barcodeSns.size(), new Date());

        int uid = SecurityUtils.getLoginUserId();

        List<ObjBarcodeImage> objImageBarCodes = new ArrayList<>();

        for (String barcodeSn : barcodeSns) {
            LOGGER.debug("更新设备二维码{}的导出时间：{}", barcodeSn, new Date());
            ObjBarcodeImage objImageBarCode = imageBarCodeRepository.findOneByBarCodeSn(barcodeSn);

            objImageBarCode.setLastUpdateBy(uid);
            objImageBarCode.setExportTime(new Date());

            objImageBarCodes.add(objImageBarCode);
        }

        imageBarCodeRepository.save(objImageBarCodes);

        LOGGER.debug("更新设备二维码中导出时间成功");
    }
}
