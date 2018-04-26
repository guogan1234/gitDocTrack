/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.config;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 初始化Activiti配置
 *
 * @author Amber
 */
@Component
public class ActivitiInitConfig {

    protected static final Logger LOGGER = LoggerFactory.getLogger(ActivitiInitConfig.class);

    @Autowired
    protected RepositoryService repositoryService;

    @PostConstruct
    public void init() {
        initProcessDefinitions();
        initProcess();
    }

    /**
     * 为了让Activiti引擎知道这个流程，我们必须先进行“发布”。
     * 发布意味着引擎会把BPMN 2.0 xml解析成可以执行的东西， “发布包”中的所有流程定义都会添加到数据库中。
     * 这样，当引擎重启时，它依然可以获得“已发布”的流程。
     */
    protected void initProcessDefinitions() {
        String deploymentName = "workorder processes";
        List<Deployment> deploymentList = repositoryService.createDeploymentQuery().deploymentName(deploymentName).list();

        if (deploymentList == null || deploymentList.isEmpty()) {
            Deployment deployment = repositoryService.createDeployment()
                    .name(deploymentName)
                    .addClasspathResource("diagrams/workorder/workorder.bpmn")
                    .deploy();

            LOGGER.debug("流程【{}】发布成功，流程ID为：{}", deploymentName, deployment.getId());
        }


    }

    protected void initProcess() {

        String deploymentNameOfStock = "stockWorkOrder processes";
        List<Deployment> deploymentListOfStock = repositoryService.createDeploymentQuery().deploymentName(deploymentNameOfStock).list();

        if (deploymentListOfStock == null || deploymentListOfStock.isEmpty()) {
            Deployment deployment = repositoryService.createDeployment()
                    .name(deploymentNameOfStock)
                    .addClasspathResource("diagrams/workorder/stockWorkOrder.bpmn")
                    .deploy();

            LOGGER.debug("流程【{}】发布成功，流程ID为：{}", deploymentNameOfStock, deployment.getId());
        }

    }
}