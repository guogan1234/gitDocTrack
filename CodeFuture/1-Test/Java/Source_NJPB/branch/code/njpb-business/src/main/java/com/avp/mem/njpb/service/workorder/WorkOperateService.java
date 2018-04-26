/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.service.workorder;

import com.avp.mem.njpb.api.util.SecurityUtils;
import com.avp.mem.njpb.entity.stock.ObjStockWorkOrderHistory;
import com.avp.mem.njpb.entity.system.SysUserPositionRecord;
import com.avp.mem.njpb.entity.workorder.ObjWorkOrderBadComponent;
import com.avp.mem.njpb.entity.workorder.ObjWorkOrderOperation;
import com.avp.mem.njpb.entity.workorder.ObjWorkOrderResource;
import com.avp.mem.njpb.repository.stock.ObjStockWorkOrderHistoryRepository;
import com.avp.mem.njpb.repository.sys.UserPositionRecordRepository;
import com.avp.mem.njpb.repository.workorder.ObjWorkOrderBadComponentRepository;
import com.avp.mem.njpb.repository.workorder.ObjWorkOrderRepository;
import com.avp.mem.njpb.repository.workorder.ObjWorkOrderResourceRepository;
import com.avp.mem.njpb.repository.workorder.ObjWorkorderOperationRepository;
import com.avp.mem.njpb.util.BusinessRefData;
import com.avp.mem.njpb.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class WorkOperateService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkOperateService.class);

    @Autowired
    private ObjWorkorderOperationRepository objWorkorderOperationRepository;

    @Autowired
    ObjStockWorkOrderHistoryRepository objStockWorkOrderHistoryRepository;

    @Autowired
    protected ObjWorkOrderRepository objWorkOrderRepository;

    @Autowired
    private ObjWorkOrderBadComponentRepository objWorkOrderBadComponentRepository;

    @Autowired
    private ObjWorkOrderResourceRepository objWorkOrderResourceRepository;

    @Autowired
    UserPositionRecordRepository userPositionRecordRepository;

    /*
     * 工单操作记录保存
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateWorkOrderOperateDetail(Integer workOrderId, Integer status, Double longitude, Double latitude, String location, Double workOrderScore, Double workOrderScoreDeduct) {
        LOGGER.debug("更新工单(" + workOrderId + ")的操作记录,本次操作工单状态为 : " + status);

        Integer uid = SecurityUtils.getLoginUserId();
        ObjWorkOrderOperation objWorkorderOperation = new ObjWorkOrderOperation();
        objWorkorderOperation.setWorkOrderId(workOrderId);
        objWorkorderOperation.setOperatorId(uid);
        objWorkorderOperation.setOperationTypeId(status);
        objWorkorderOperation.setLatitude(latitude);
        objWorkorderOperation.setLongitude(longitude);
        objWorkorderOperation.setLocation(location);
        objWorkorderOperation.setWorkOrderScore(workOrderScore);
        objWorkorderOperation.setWorkOrderScoreDeduct(workOrderScoreDeduct);

        ObjWorkOrderOperation lastWorkOrderOperation = objWorkorderOperationRepository
                .findTopByWorkOrderIdOrderByCreateTimeDesc(workOrderId);

        LOGGER.debug("查询工单上次操作时间成功");
        Date lastOperationDateTime = new Date();

        if (lastWorkOrderOperation != null) {
            lastOperationDateTime = lastWorkOrderOperation.getCreateTime();
        }
        objWorkorderOperation.setLastOperationTime(lastOperationDateTime);
        objWorkorderOperationRepository.save(objWorkorderOperation);

        if (Validator.isNotNull(longitude) && Validator.isNotNull(latitude)) {
            Integer positionSource = 0;
            if (BusinessRefData.WOSTATUS_WO_CREATED.equals(status) || BusinessRefData.WOSTATUS_WO_MODIFY.equals(status)) {
                positionSource = BusinessRefData.POSITIONSOURCE_REPORT;
            } else if (BusinessRefData.WOSTATUS_RPR_CONFIRM.equals(status)) {
                positionSource = BusinessRefData.POSITIONSOURCE_CONFIRM;
            } else if (BusinessRefData.WOSTATUS_WO_ARRIVE.equals(status)) {
                positionSource = BusinessRefData.POSITIONSOURCE_ARRIVE;
            } else if (BusinessRefData.WOSTATUS_RPR_COMPLATED.equals(status)) {
                positionSource = BusinessRefData.POSITIONSOURCE_FINISH;
            } else if (BusinessRefData.WOSTATUS_WO_UNHANDLED.equals(status)) {
                positionSource = BusinessRefData.POSITIONSOURCE_LEAVE;
            }

            SysUserPositionRecord sysUserPositionRecord = new SysUserPositionRecord(uid, longitude, latitude, location, positionSource, workOrderId, new Date());

            userPositionRecordRepository.save(sysUserPositionRecord);
        }
    }

//    /*
//     * 更换备件
//     */
//    @Transactional(propagation = Propagation.REQUIRED)
//    public ResultEntity replaceComponent(List<Integer> badEstateIds, List<Integer> goodEstateIds, Integer workOrderId) {
//
//    }

    /*
     * 库存工单操作记录保存
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveStockWorkOrderOperateHistory(Integer stockWorkOrderId, Integer stockWorkOrderStatusId, Integer processUserId) {
        LOGGER.debug("添加库存工单id：{},本次操作库存工单状态为 : {}，操作人是：{}", stockWorkOrderId, stockWorkOrderStatusId, processUserId);
        Integer uid = SecurityUtils.getLoginUserId();
        ObjStockWorkOrderHistory objStockWorkOrderHistory = new ObjStockWorkOrderHistory();
        objStockWorkOrderHistory.setStockWorkOrderId(stockWorkOrderId);
        objStockWorkOrderHistory.setStockWorkOrderStatusId(stockWorkOrderStatusId);
        objStockWorkOrderHistory.setProcessUserId(processUserId);
        objStockWorkOrderHistory.setOperationTime(new Date());

        ObjStockWorkOrderHistory lastStockWorkOrderOperation = objStockWorkOrderHistoryRepository.findTopByStockWorkOrderIdOrderByCreateTimeDesc(stockWorkOrderId);
        Date lastOperationDateTime = new Date();

        if (lastStockWorkOrderOperation != null) {
            lastOperationDateTime = lastStockWorkOrderOperation.getLastOperationTime();
        }
        objStockWorkOrderHistory.setLastOperationTime(lastOperationDateTime);
        objStockWorkOrderHistoryRepository.save(objStockWorkOrderHistory);
    }

    /*
     * 删除工单资源数据
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteWorkOrderResource(Integer workOrderId) {
        LOGGER.debug("需要删除资源的工单id：{}", workOrderId);

        List<ObjWorkOrderBadComponent> objWorkOrderBadComponentList = new ArrayList<>();
        List<ObjWorkOrderResource> objWorkOrderResourceList = new ArrayList<>();

        objWorkOrderBadComponentRepository.findByWorkOrderId(workOrderId).forEach(objWorkOrderBadComponent -> {
            objWorkOrderBadComponent.setRemoveTime(new Date());
            objWorkOrderBadComponentList.add(objWorkOrderBadComponent);
        });

        objWorkOrderBadComponentRepository.save(objWorkOrderBadComponentList);

        LOGGER.debug("工单：{}的坏件记录删除成功,数据量为：{}", workOrderId, objWorkOrderBadComponentList.size());

        objWorkOrderResourceRepository.findByWorkOrderIdAndCategory(workOrderId, BusinessRefData.WO_RESOURCE_CATEGORY_REPORT).forEach(objWorkOrderResource -> {
            objWorkOrderResource.setRemoveTime(new Date());
            objWorkOrderResourceList.add(objWorkOrderResource);
        });
        objWorkOrderResourceRepository.save(objWorkOrderResourceList);
        LOGGER.debug("工单：{}的图片记录删除成功,数据量为：{}", workOrderId, objWorkOrderResourceList.size());

    }
}
