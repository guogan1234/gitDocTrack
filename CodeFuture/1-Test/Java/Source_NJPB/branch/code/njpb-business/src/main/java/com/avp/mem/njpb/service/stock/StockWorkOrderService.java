/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.service.stock;

import com.avp.mem.njpb.entity.stock.ObjStockRecord;
import com.avp.mem.njpb.entity.stock.ObjStockWorkOrderDetail;
import com.avp.mem.njpb.entity.stock.ObjStockWorkOrderResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by six on 2017/8/17.
 */
@Service
public class StockWorkOrderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StockWorkOrderService.class);

    @Transactional(propagation = Propagation.REQUIRED)
    public List<ObjStockWorkOrderDetail> saveStockWorkOrderDetail(List<ObjStockRecord> objStockRecords, Integer stockWorkOrderId) {
        if (objStockRecords == null || stockWorkOrderId == null) {
            LOGGER.debug("传入参数对象为空");
            throw new RuntimeException("传入参数对象为空");
        }
        List<ObjStockWorkOrderDetail> stockWorkOrderDetails = new ArrayList<>();
        for (ObjStockRecord temp : objStockRecords) {
            LOGGER.debug("申请设备类型：{}，申请数量：{}", temp.getEstateTypeId(), temp.getCount());
            ObjStockWorkOrderDetail objStockWorkOrderDetail = new ObjStockWorkOrderDetail();
            objStockWorkOrderDetail.setStockWorkOrderId(stockWorkOrderId);
            objStockWorkOrderDetail.setEstateTypeId(temp.getEstateTypeId());
            objStockWorkOrderDetail.setCount(temp.getCount());
            stockWorkOrderDetails.add(objStockWorkOrderDetail);
        }
        return stockWorkOrderDetails;
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public List<ObjStockWorkOrderResource> saveStockWorkOrderResources(List<ObjStockWorkOrderResource> objStockWorkOrderResources, Integer stockWorkOrderId) {
        if (objStockWorkOrderResources == null || stockWorkOrderId == null) {
            LOGGER.debug("传入参数对象为空");
            throw new RuntimeException("传入参数对象为空");
        }
        List<ObjStockWorkOrderResource> stockWorkOrderResources = new ArrayList<>();
        for (ObjStockWorkOrderResource temp : objStockWorkOrderResources) {
            ObjStockWorkOrderResource stockWorkOrderResource = new ObjStockWorkOrderResource();
            stockWorkOrderResource.setStockWorkOrderId(stockWorkOrderId);
            stockWorkOrderResource.setFileId(temp.getFileId());
            stockWorkOrderResources.add(stockWorkOrderResource);
        }
        return stockWorkOrderResources;
    }
}
