/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.resource.workorder;

import com.avp.mem.njpb.api.rest.ResponseBuilder;
import com.avp.mem.njpb.api.rest.ResponseCode;
import com.avp.mem.njpb.api.rest.RestBody;
import com.avp.mem.njpb.api.util.SecurityUtils;
import com.avp.mem.njpb.entity.view.VwUserWorkOrder;
import com.avp.mem.njpb.entity.view.VwWorkOrder;
import com.avp.mem.njpb.repository.basic.ObjFileRepository;
import com.avp.mem.njpb.repository.workorder.ObjWorkOrderBadComponentRepository;
import com.avp.mem.njpb.repository.workorder.ObjWorkOrderRepository;
import com.avp.mem.njpb.repository.workorder.ObjWorkOrderResourceRepository;
import com.avp.mem.njpb.service.activiti.ActivitiService;
import com.avp.mem.njpb.service.activiti.WorkOrderWorkflowService;
import com.avp.mem.njpb.service.workorder.WorkOperateService;
import com.avp.mem.njpb.util.FileUploadUtil;
import com.avp.mem.njpb.util.Validator;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;


/**
 * 工单工作流获取数据
 *
 * @author Amber
 */
@RestController
public class WorkOrderFlowController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkOrderFlowController.class);

    @Autowired
    protected RuntimeService runtimeService;

    @Autowired
    protected TaskService taskService;

    @Autowired
    protected ObjWorkOrderRepository objWorkOrderRepository;

    @Autowired
    private WorkOperateService workOperateService;

    @Autowired
    private FileUploadUtil fileUploadUtil;

    @Autowired
    private ObjFileRepository objFileRepository;

    @Autowired
    private ObjWorkOrderBadComponentRepository objWorkOrderBadComponentRepository;

    @Autowired
    private ObjWorkOrderResourceRepository objWorkOrderResourceRepository;

    @Autowired
    private WorkOrderWorkflowService workorderReportWorkflowService;

    @Autowired
    private ActivitiService activitiService;

    /**
     * 查询工单待办事项--APP接口--上拉加载更多--无数据权限
     *
     * @param operationTime
     * @param page
     * @return ResponseEntity<RestBody<VwWorkOrder>>
     */
    @GetMapping("workOrders/findTodoTaskByOperationTimeLessThan")
    public ResponseEntity<RestBody<VwWorkOrder>> findTodoTaskByOperationTimeLessThan(Date operationTime, Pageable page) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            Integer uid = SecurityUtils.getLoginUserId();

            Page<VwWorkOrder> vwWorkOrders = workorderReportWorkflowService.findTodoTasksByUserIdAndOperationTimeLessThan(uid, operationTime, page);
            if (Validator.isNotNull(vwWorkOrders)) {

                LOGGER.debug("查询待处理工单--上拉加载更多成功，数据量为：{},总页数为：{}", vwWorkOrders.getTotalElements(), vwWorkOrders.getTotalPages());
            }

            builder.setResultEntity(vwWorkOrders, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.debug("查询工单待办事项--APP接口--上拉加载更多查询失败");
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }

        return builder.getResponseEntity();
    }


    /**
     * 查询工单待办事项--APP接口--下拉刷新--无数据权限
     *
     * @param operationTime
     * @return ResponseEntity<RestBody<VwWorkOrder>>
     */
    @GetMapping("workOrders/findTodoTaskByOperationTimeGreaterThan")
    public ResponseEntity<RestBody<VwWorkOrder>> findTodoTaskByOperationTimeGreaterThan(Date operationTime) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            Integer uid = SecurityUtils.getLoginUserId();

            List<VwWorkOrder> vwWorkOrders = workorderReportWorkflowService.findTodoTasksByUserIdAndOperationTimeGreaterThan(uid, operationTime);

            LOGGER.debug("查询待处理工单--上拉加载更多成功，数据量为：{}", vwWorkOrders.size());
            builder.setResultEntity(vwWorkOrders, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.debug("查询工单待办事项--APP接口--上拉加载更多查询失败");
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }

        return builder.getResponseEntity();
    }

    /**
     * 查询处理中工单--APP接口--上拉加载更多--有数据权限
     *
     * @param operationTime
     * @param page
     * @return ResponseEntity<RestBody<VwWorkOrder>>
     */
    @GetMapping("workOrders/findRunningTaskByOperationTimeLessThan")
    public ResponseEntity<RestBody<VwUserWorkOrder>> findRunningTaskByOperationTimeLessThan(Date operationTime, Pageable page) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            Integer uid = SecurityUtils.getLoginUserId();

            Page<VwUserWorkOrder> vwWorkOrders = workorderReportWorkflowService.findRunningTasksByUserIdAndOperationTimeLessThan(uid, operationTime, page);

            LOGGER.debug("查询处理中工单--上拉加载更多成功，数据量为：{},总页数为：{}", vwWorkOrders.getTotalElements(), vwWorkOrders.getTotalPages());
            builder.setResultEntity(vwWorkOrders, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.debug("查询处理中工单--APP接口--上拉加载更多查询失败");
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }

        return builder.getResponseEntity();
    }

    /**
     * 查询处理中工单--APP接口--下拉刷新--有数据权限
     *
     * @param operationTime
     * @return ResponseEntity<RestBody<VwWorkOrder>>
     */
    @GetMapping("workOrders/findRunningTaskByOperationTimeGreaterThan")
    public ResponseEntity<RestBody<VwUserWorkOrder>> findRunningTaskByOperationTimeGreaterThan(Date operationTime) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            Integer uid = SecurityUtils.getLoginUserId();

            List<VwUserWorkOrder> vwWorkOrders = workorderReportWorkflowService.findRunningTasksByUserIdAndOperationTimeGreaterThan(uid, operationTime);

            LOGGER.debug("查询处理中工单--下拉刷新成功，数据量为：{}", vwWorkOrders.size());
            builder.setResultEntity(vwWorkOrders, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.debug("查询处理中工单--APP接口--上拉加载更多查询失败");
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }

        return builder.getResponseEntity();
    }
}
