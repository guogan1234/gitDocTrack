/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.config.handler;

import com.avp.mem.njpb.entity.workorder.ObjWorkOrder;
import com.avp.mem.njpb.repository.workorder.ObjWorkOrderRepository;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by Amber Wang on 2017-08-18 下午 06:04.
 */
@Component
public class CustomTakeEventHandler implements ExecutionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomTakeEventHandler.class);

    @Autowired
    private IdentityService identityService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private ObjWorkOrderRepository objWorkOrderRepository;

    @PostConstruct
    void init() {
        LOGGER.debug("bean customTakeEventHandler is creating...");
    }

    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {

        String processInstanceId = delegateExecution.getProcessInstanceId();
        LOGGER.debug("take事件触发，流程id为：{}", processInstanceId);

        ObjWorkOrder objWorkOrder = objWorkOrderRepository.findByProcessInstanceId(processInstanceId);

        Integer assignEmployee = objWorkOrder.getAssignEmployee();
        LOGGER.debug("工单：{}的调度人为：{}", objWorkOrder.getId(), assignEmployee);

        delegateExecution.setVariable("taskAssignEmployee", assignEmployee);
    }
}
