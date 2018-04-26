/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.service.stock;

import com.avp.mem.njpb.entity.stock.ObjStockRecordPersonalHistory;
import com.avp.mem.njpb.repository.stock.ObjStockRecordPersonalHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by six on 2017/8/16.
 */
@Service
public class StockRecordPersonalHistory {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockRecordPersonalHistory.class);

    @Autowired
    ObjStockRecordPersonalHistoryRepository objStockRecordPersonalHistoryRepository;

    //@Transactional(propagation = Propagation.REQUIRED)
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveStockRecordPersonalHistory(Integer estateTypeId, Integer count, Integer operationType, Date operationTime, Integer userId) {
        LOGGER.debug("设备类型：{}，数量：{}，操作类型：{}，操作人：{}", estateTypeId, count, operationType, operationTime, userId);
        ObjStockRecordPersonalHistory objStockRecordPersonalHistory = new ObjStockRecordPersonalHistory();
        objStockRecordPersonalHistory.setCount(count);
        objStockRecordPersonalHistory.setEstateTypeId(estateTypeId);
        objStockRecordPersonalHistory.setOperationType(operationType);
        objStockRecordPersonalHistory.setOperationTime(operationTime);
        objStockRecordPersonalHistory.setUserId(userId);
        objStockRecordPersonalHistory = objStockRecordPersonalHistoryRepository.save(objStockRecordPersonalHistory);
        LOGGER.debug("个人仓库历史记录添加成功,id:{}", objStockRecordPersonalHistory.getId());
    }


}
