/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.config.handler;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by Amber Wang on 2017-08-18 下午 06:04.
 */
@Component
public class CustomCreateEventHandler implements TaskListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomCreateEventHandler.class);

    @Autowired
    private IdentityService identityService;

    @Autowired
    private RuntimeService runtimeService;

    @PostConstruct
    void init() {
        LOGGER.debug("bean customCreateEventHandler is creating...");
    }

    /**
     * 监听每个task的create事件
     *
     * @param delegateTask
     */
    @Override
    public void notify(DelegateTask delegateTask) {

        String assignEmployee = (String) runtimeService.getVariable(delegateTask.getExecutionId(), "taskAssignEmployee");

        String processInstanceId = delegateTask.getProcessInstanceId();

        String taskDefinitionKey = delegateTask.getTaskDefinitionKey();

        String currentActivityId = delegateTask.getExecution().getCurrentActivityId();

        LOGGER.debug("流程id为：{},taskDefinitionKey:{},currentActivityId:{},assignEmployee:{}", processInstanceId, taskDefinitionKey, currentActivityId, assignEmployee);

        delegateTask.setAssignee(assignEmployee);
    }
}
