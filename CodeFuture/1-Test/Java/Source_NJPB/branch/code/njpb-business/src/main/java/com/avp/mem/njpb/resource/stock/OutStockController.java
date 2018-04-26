/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.resource.stock;

import com.avp.mem.njpb.api.rest.ResponseBuilder;
import com.avp.mem.njpb.api.rest.ResponseCode;
import com.avp.mem.njpb.api.rest.RestBody;
import com.avp.mem.njpb.api.util.SecurityUtils;
import com.avp.mem.njpb.entity.estate.ObjEstate;
import com.avp.mem.njpb.entity.stock.ObjInventoryRecord;
import com.avp.mem.njpb.entity.stock.ObjInventoryRecordDetail;
import com.avp.mem.njpb.entity.system.SysUser;
import com.avp.mem.njpb.entity.entityBO.ObjInventoryRecordBO;
import com.avp.mem.njpb.repository.estate.ObjEstateRepository;
import com.avp.mem.njpb.repository.stock.ObjInventoryRecordDetailRepository;
import com.avp.mem.njpb.repository.stock.ObjInventoryRecordRepository;
import com.avp.mem.njpb.repository.sys.SysUserRepository;
import com.avp.mem.njpb.util.BusinessRefData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by six on 2017/8/8.
 */
@RestController
public class OutStockController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OutStockController.class);


    @Autowired
    ObjInventoryRecordRepository objInventoryRecordRepository;

    @Autowired
    ObjEstateRepository objEstateRepository;


    @Autowired
    ObjInventoryRecordDetailRepository objInventoryRecordDetailRepository;

    @Autowired
    SysUserRepository sysUserRepository;


    /**
     * 出库
     *
     * @param objInventoryRecordBOs
     * @return
     */
    @RequestMapping(value = "inventories/outStock", method = RequestMethod.POST)
    @Transactional
    ResponseEntity<RestBody<ObjEstate>> saveObjInventoryRecordOut(@RequestBody List<ObjInventoryRecordBO> objInventoryRecordBOs) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            Integer uid = SecurityUtils.getLoginUserId();
            SysUser sysUser = sysUserRepository.findOne(uid);
            LOGGER.debug("当前登录账户：{}", sysUser.getUserAccount());

            List<ObjInventoryRecord> objInventoryRecords = new ArrayList<>();
            for (ObjInventoryRecordBO temp : objInventoryRecordBOs) {
                Integer corpId = sysUser.getCorpId();
                Integer count = temp.getBarCodes().size();
                Integer estateTypeId = temp.getEstateTypeId();

                //添加出库记录
                ObjInventoryRecord objInventoryRecord = new ObjInventoryRecord();
                objInventoryRecord.setCorpId(corpId);
                objInventoryRecord.setCount(count);
                objInventoryRecord.setEstateTypeId(estateTypeId);
                objInventoryRecord.setOperationType(BusinessRefData.OPERATION_TYPE_OUT_STOCK);
                objInventoryRecord.setStockId(corpId);
                objInventoryRecord = objInventoryRecordRepository.save(objInventoryRecord);
                LOGGER.debug("出库记录添加成功,入库记录id：{},公司id：{},数量为：{}，设备类型为：{}", objInventoryRecord.getId(), corpId, count, estateTypeId);

                //更改设备状态
                List<ObjEstate> objEstates = objEstateRepository.findByEstateSnIn(temp.getBarCodes());
                List<ObjEstate> objEstatesStatus = new ArrayList<>();
                for (ObjEstate objEstate : objEstates) {
                    String estateSn = objEstate.getEstateSn();
                    if (objEstate.getEstateStatusId() == BusinessRefData.ESTATE_OUT_STOCK) {
                        LOGGER.debug("设备已经出库，不可以再出库,设备二维码:{}", estateSn);
                        builder.setResponseCode(ResponseCode.FAILED, "设备已经出库，不可以再出库,设备二维码:" + estateSn);
                        throw new RuntimeException("设备已经出库，不可以再出库,设备二维码:" + estateSn);
                        //return builder.getResponseEntity();
                    }
                    objEstate.setEstateStatusId(BusinessRefData.ESTATE_OUT_STOCK);
                    objEstatesStatus.add(objEstate);
                }
                objEstates = objEstateRepository.save(objEstatesStatus);
                LOGGER.debug("设备状态更改成功,跟改设备数量：{},类型：{}", objEstates.size(), estateTypeId);

                //记录详情
                List<ObjInventoryRecordDetail> objInventoriesRecordDetails = new ArrayList<>();
                for (ObjEstate objEstate : objEstates) {
                    ObjInventoryRecordDetail objInventoryRecordDetail = new ObjInventoryRecordDetail();
                    objInventoryRecordDetail.setEstateId(objEstate.getId());
                    objInventoryRecordDetail.setInventoryRecordId(objInventoryRecord.getId());
                    objInventoriesRecordDetails.add(objInventoryRecordDetail);
                }
                objInventoriesRecordDetails = objInventoryRecordDetailRepository.save(objInventoriesRecordDetails);
                //关联记录跟设备关系
                LOGGER.debug("记录详情添加成功,数据量为：{}", objInventoriesRecordDetails.size());
                objInventoryRecords.add(objInventoryRecord);
            }
            builder.setResultEntity(objInventoryRecords, ResponseCode.OK);
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.FAILED, "出库失败");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return builder.getResponseEntity();
    }


}
