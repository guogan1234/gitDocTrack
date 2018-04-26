/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.resource.basic;

import com.avp.mem.njpb.api.rest.ResponseBuilder;
import com.avp.mem.njpb.api.rest.ResponseCode;
import com.avp.mem.njpb.api.rest.RestBody;
import com.avp.mem.njpb.entity.workorder.RefWorkOrderStatus;
import com.avp.mem.njpb.repository.basic.WorkOrderStatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by six on 2017/7/28.
 */
@RestController
public class WorkOrderStatusController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkOrderStatusController.class);

    @Autowired
    WorkOrderStatusRepository workOrderStatusRepository;

    /**
     * 工单类型查询
     *
     * @return
     */
    @RequestMapping(value = "workOrderStatus", method = RequestMethod.GET)
    ResponseEntity<RestBody<RefWorkOrderStatus>> findAll() {

        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            List<RefWorkOrderStatus> workOrderStatus = workOrderStatusRepository.findAll();
            LOGGER.debug("查询工单状态成功，数据量为：{}", workOrderStatus.size());
            builder.setResultEntity(workOrderStatus, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }
}
